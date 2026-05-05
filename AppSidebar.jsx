import { Outlet, useNavigate } from "react-router-dom";
import { SidebarProvider, SidebarTrigger } from "@/components/ui/sidebar";
import { AppSidebar } from "./AppSidebar";
import { useStore } from "@/lib/store";
import { Select, SelectContent, SelectItem, SelectTrigger, SelectValue } from "@/components/ui/select";
import { Bell, ShieldCheck, User, Users } from "lucide-react";
import { Badge } from "@/components/ui/badge";
const roleLabel = { admin: "Owner / Admin", tenant: "Tenant", guard: "Security Guard" };
const roleIcon = { admin: Users, tenant: User, guard: ShieldCheck };
export function AppLayout() {
    const role = useStore((s) => s.currentRole);
    const setRole = useStore((s) => s.setRole);
    const tenants = useStore((s) => s.tenants);
    const currentTenantId = useStore((s) => s.currentTenantId);
    const setCurrentTenant = useStore((s) => s.setCurrentTenant);
    const isLoading = useStore((s) => s.isLoading);
    const error = useStore((s) => s.error);
    const clearError = useStore((s) => s.clearError);
    const pendingVisitors = useStore((s) => s.visitors.filter((v) => v.status === "pending").length);
    const navigate = useNavigate();
    const Icon = roleIcon[role];
    const onRoleChange = (r) => {
        setRole(r);
        if (r === "admin")
            navigate("/admin");
        else if (r === "tenant")
            navigate("/tenant");
        else
            navigate("/guard");
    };
    return (<SidebarProvider>
      <div className="min-h-screen flex w-full bg-background">
        <AppSidebar />
        <div className="flex-1 flex flex-col min-w-0">
          <header className="h-14 border-b bg-card flex items-center px-3 md:px-6 gap-3 sticky top-0 z-30">
            <SidebarTrigger />
            <div className="flex items-center gap-2">
              <Icon className="h-4 w-4 text-primary"/>
              <span className="font-semibold text-sm hidden sm:inline">{roleLabel[role]}</span>
            </div>
            <div className="ml-auto flex items-center gap-2 md:gap-3">
              {pendingVisitors > 0 && (<Badge variant="outline" className="border-warning text-warning bg-warning/10 gap-1">
                  <Bell className="h-3 w-3"/> {pendingVisitors} pending
                </Badge>)}
              {role === "tenant" && (<Select value={currentTenantId ?? undefined} onValueChange={setCurrentTenant}>
                  <SelectTrigger className="h-8 w-[160px] text-xs">
                    <SelectValue placeholder="Select tenant"/>
                  </SelectTrigger>
                  <SelectContent>
                    {tenants.map((t) => (<SelectItem key={t.id} value={t.id} className="text-xs">{t.name}</SelectItem>))}
                  </SelectContent>
                </Select>)}
              <Select value={role} onValueChange={(v) => onRoleChange(v)}>
                <SelectTrigger className="h-8 w-[150px] text-xs">
                  <SelectValue />
                </SelectTrigger>
                <SelectContent>
                  <SelectItem value="admin">Owner / Admin</SelectItem>
                  <SelectItem value="tenant">Tenant</SelectItem>
                  <SelectItem value="guard">Security Guard</SelectItem>
                </SelectContent>
              </Select>
            </div>
          </header>
          {isLoading && <div className="px-4 md:px-6 py-2 text-xs text-muted-foreground border-b bg-muted/30">Syncing latest data...</div>}
          {error && (<div className="px-4 md:px-6 py-2 text-xs border-b bg-destructive/10 text-destructive flex items-center justify-between gap-2">
              <span>{error}</span>
              <button className="underline" onClick={clearError}>Dismiss</button>
            </div>)}
          <main className="flex-1 p-4 md:p-6 max-w-[1400px] w-full mx-auto">
            <Outlet />
          </main>
        </div>
      </div>
    </SidebarProvider>);
}
