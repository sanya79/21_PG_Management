import { create } from "zustand";
import { api } from "@/lib/api";

const uiRoleKey = "pg-ui-role";
const uiTenantKey = "pg-ui-tenant";
const uiBuildingKey = "pg-ui-building";

const defaultBuilding = { name: "Sunrise PG", floors: 3, roomsPerFloor: 4, bedsPerRoom: 3 };

const readJson = (key, fallback) => {
  try {
    const raw = localStorage.getItem(key);
    return raw ? JSON.parse(raw) : fallback;
  } catch {
    return fallback;
  }
};

const getInitialRole = () => localStorage.getItem(uiRoleKey) || "admin";
const getInitialTenant = () => localStorage.getItem(uiTenantKey) || "t1";
const getInitialBuilding = () => readJson(uiBuildingKey, defaultBuilding);

const mergeRoomsWithBeds = (rooms = [], beds = []) =>
  rooms.map((room) => ({
    ...room,
    beds: beds.filter((bed) => bed.roomId === room.id),
  }));

const hydrateAll = async () => {
  const [tenants, rooms, beds, rents, complaints, notices, visitors] = await Promise.all([
    api.get("/api/tenants"),
    api.get("/api/property/rooms"),
    api.get("/api/property/beds"),
    api.get("/api/rents"),
    api.get("/api/complaints"),
    api.get("/api/notices"),
    api.get("/api/visitors"),
  ]);

  return {
    tenants,
    rooms: mergeRoomsWithBeds(rooms, beds),
    rents,
    complaints,
    notices,
    visitors,
  };
};

export const useStore = create((set, get) => ({
  currentRole: getInitialRole(),
  currentTenantId: getInitialTenant(),
  building: getInitialBuilding(),

  isLoading: true,
  error: null,

  rooms: [],
  tenants: [],
  rents: [],
  complaints: [],
  notices: [],
  visitors: [],

  setRole: (role) => {
    localStorage.setItem(uiRoleKey, role);
    set({ currentRole: role });
  },

  setCurrentTenant: (id) => {
    localStorage.setItem(uiTenantKey, id);
    set({ currentTenantId: id });
  },

  clearError: () => set({ error: null }),

  initializeData: async () => {
    try {
      set({ isLoading: true, error: null });
      const data = await hydrateAll();

      const preferredTenantId = get().currentTenantId;
      const validTenantId = data.tenants.some((t) => t.id === preferredTenantId)
        ? preferredTenantId
        : data.tenants[0]?.id || null;

      if (validTenantId) {
        localStorage.setItem(uiTenantKey, validTenantId);
      }

      set({ ...data, currentTenantId: validTenantId, isLoading: false });
    } catch (error) {
      set({ error: error.message || "Failed to load data", isLoading: false });
    }
  },

  saveBuilding: async (building) => {
    localStorage.setItem(uiBuildingKey, JSON.stringify(building));
    set({ building });

    const rooms = [];
    const beds = [];

    for (let floor = 1; floor <= building.floors; floor += 1) {
      for (let roomNum = 1; roomNum <= building.roomsPerFloor; roomNum += 1) {
        const id = `r-${floor}-${roomNum}`;
        const number = `${floor}0${roomNum}`;
        rooms.push({ id, floor, number });
        for (let bedNum = 1; bedNum <= building.bedsPerRoom; bedNum += 1) {
          beds.push({ id: `${id}-b${bedNum}`, roomId: id, label: `B${bedNum}`, tenantId: null });
        }
      }
    }

    try {
      await Promise.all(rooms.map((room) => api.post("/api/property/rooms", room)));
      await Promise.all(beds.map((bed) => api.post("/api/property/beds", bed)));
      const [savedRooms, savedBeds] = await Promise.all([
        api.get("/api/property/rooms"),
        api.get("/api/property/beds"),
      ]);
      set({ rooms: mergeRoomsWithBeds(savedRooms, savedBeds) });
    } catch (error) {
      set({ error: error.message || "Failed to save property setup" });
    }
  },

  assignTenantToBed: async (tenantId, bedId) => {
    try {
      await api.post("/api/tenants/assign-bed", { tenantId, bedId });
      const data = await hydrateAll();
      set(data);
    } catch (error) {
      set({ error: error.message || "Failed to assign tenant" });
      throw error;
    }
  },

  unassignBed: async (bedId) => {
    try {
      await api.post("/api/tenants/unassign-bed", { bedId });
      const data = await hydrateAll();
      set(data);
    } catch (error) {
      set({ error: error.message || "Failed to unassign bed" });
      throw error;
    }
  },

  addTenant: async (tenant) => {
    try {
      const created = await api.post("/api/tenants", {
        name: tenant.name,
        phone: tenant.phone,
        kycId: tenant.kycId,
        roomId: tenant.roomId || null,
        bedId: null,
      });

      if (tenant.bedId) {
        await api.post("/api/tenants/assign-bed", { tenantId: created.id, bedId: tenant.bedId });
      }

      const data = await hydrateAll();
      set(data);
      return created;
    } catch (error) {
      set({ error: error.message || "Failed to add tenant" });
      throw error;
    }
  },

  removeTenant: async (id) => {
    try {
      await api.delete(`/api/tenants/${id}`);
      const data = await hydrateAll();
      set(data);
    } catch (error) {
      set({ error: error.message || "Failed to remove tenant" });
      throw error;
    }
  },

  addRent: async (rent) => {
    try {
      await api.post("/api/rents", rent);
      set({ rents: await api.get("/api/rents") });
    } catch (error) {
      set({ error: error.message || "Failed to add rent record" });
      throw error;
    }
  },

  submitRentProof: async (id, proof) => {
    try {
      const updated = await api.patch(`/api/rents/${id}/proof`, { proof });
      set({ rents: get().rents.map((rent) => (rent.id === id ? updated : rent)) });
    } catch (error) {
      set({ error: error.message || "Failed to submit rent proof" });
      throw error;
    }
  },

  markRentPaid: async (id) => {
    try {
      const updated = await api.patch(`/api/rents/${id}/paid`);
      set({ rents: get().rents.map((rent) => (rent.id === id ? updated : rent)) });
    } catch (error) {
      set({ error: error.message || "Failed to mark rent paid" });
      throw error;
    }
  },

  addComplaint: async (complaint) => {
    try {
      const created = await api.post("/api/complaints", complaint);
      set({ complaints: [created, ...get().complaints] });
    } catch (error) {
      set({ error: error.message || "Failed to submit complaint" });
      throw error;
    }
  },

  setComplaintStatus: async (id, status) => {
    try {
      const updated = await api.patch(`/api/complaints/${id}/status`, { status });
      set({ complaints: get().complaints.map((complaint) => (complaint.id === id ? updated : complaint)) });
    } catch (error) {
      set({ error: error.message || "Failed to update complaint status" });
      throw error;
    }
  },

  addNotice: async (notice) => {
    try {
      const created = await api.post("/api/notices", notice);
      set({ notices: [created, ...get().notices] });
    } catch (error) {
      set({ error: error.message || "Failed to post notice" });
      throw error;
    }
  },

  addVisitor: async (visitor) => {
    try {
      const created = await api.post("/api/visitors", visitor);
      set({ visitors: [created, ...get().visitors] });
    } catch (error) {
      set({ error: error.message || "Failed to create visitor entry" });
      throw error;
    }
  },

  setVisitorStatus: async (id, status) => {
    try {
      const endpoint = status === "approved"
        ? `/api/visitors/${id}/approve`
        : status === "denied"
          ? `/api/visitors/${id}/deny`
          : `/api/visitors/${id}/status`;

      const updated = status === "approved" || status === "denied"
        ? await api.patch(endpoint)
        : await api.patch(endpoint, { status });

      set({ visitors: get().visitors.map((visitor) => (visitor.id === id ? updated : visitor)) });
    } catch (error) {
      set({ error: error.message || "Failed to update visitor status" });
      throw error;
    }
  },

  checkOutVisitor: async (id) => {
    try {
      const updated = await api.patch(`/api/visitors/${id}/checkout`);
      set({ visitors: get().visitors.map((visitor) => (visitor.id === id ? updated : visitor)) });
    } catch (error) {
      set({ error: error.message || "Failed to check out visitor" });
      throw error;
    }
  },

  resetAll: async () => {
    await get().initializeData();
  },
}));

export const formatDateTime = (iso) =>
  iso ? new Date(iso).toLocaleString([], { dateStyle: "short", timeStyle: "short" }) : "-";
