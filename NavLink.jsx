import { NavLink, useLocation } from "react-router-dom";
import { Sidebar, SidebarContent, SidebarGroup, SidebarGroupContent, SidebarGroupLabel, SidebarMenu, SidebarMenuButton, SidebarMenuItem, SidebarHeader, SidebarFooter, useSidebar, } from "@/components/ui/sidebar";
import { useStore } from "@/lib/store";
import { LayoutDashboard, Building2, Users, Wallet, MessageSquareWarning, Megaphone, ShieldCheck, UserCheck, ClipboardList, Home, RefreshCcw, } from "lucide-react";
import { Button } from "@/components/ui/button";
const navByRole = {
    admin: [
        { to: "/admin", label: "Dashboard", icon: LayoutDashboard },
        { to: "/admin/property", label: "Property", icon: Building2 },
        { to: "/admin/tenants", label: "Tenants", icon: Users },
        { to: "/admin/rent", label: "Rent", icon: Wallet },
        { to: "/admin/complaints", label: "Complaints", icon: MessageSquareWarning },
        { to: "/admin/notices", label: "Notices", icon: Megaphone },
    ],
    tenant: [
        { to: "/tenant", label: "Home", icon: Home },
        { to: "/tenant/rent", label: "Rent", icon: Wallet },
        { to: "/tenant/visitors", label: "Visitors", icon: UserCheck },
        { to: "/tenant/complaints", label: "Complaints", icon: MessageSquareWarning },
        { to: "/tenant/notices", label: "Notices", icon: Megaphone },
    ],
    guard: [
        { to: "/guard", label: "Visitor Entry", icon: ShieldCheck },
        { to: "/guard/log", label: "Visitor Log", icon: ClipboardList },
    ],
};
export function AppSidebar() {
    const { state } = useSidebar();
    const collapsed = state === "collapsed";
    const role = useStore((s) => s.currentRole);
    const resetAll = useStore((s) => s.resetAll);
    const { pathname } = useLocation();
    const items = navByRole[role];
    return (<Sidebar collapsible="icon">
      <SidebarHeader className="border-b border-sidebar-border">
        <div className="flex items-center gap-2 px-2 py-1">
          <div className="h-8 w-8 rounded-md gradient-primary flex items-center justify-center text-primary-foreground font-bold">
            PG
          </div>
          {!collapsed && (<div className="leading-tight">
              <div className="text-sm font-semibold text-sidebar-foreground">PG Manager</div>
              <div className="text-xs text-sidebar-foreground/60 capitalize">{role} portal</div>
            </div>)}
        </div>
      </SidebarHeader>
      <SidebarContent>
        <SidebarGroup>
          <SidebarGroupLabel>Navigation</SidebarGroupLabel>
          <SidebarGroupContent>
            <SidebarMenu>
              {items.map((item) => {
            const active = pathname === item.to;
            return (<SidebarMenuItem key={item.to}>
                    <SidebarMenuButton asChild isActive={active}>
                      <NavLink to={item.to} className="flex items-center gap-2">
                        <item.icon className="h-4 w-4"/>
                        {!collapsed && <span>{item.label}</span>}
                      </NavLink>
                    </SidebarMenuButton>
                  </SidebarMenuItem>);
        })}
            </SidebarMenu>
          </SidebarGroupContent>
        </SidebarGroup>
      </SidebarContent>
      <SidebarFooter className="border-t border-sidebar-border">
        {!collapsed && (<Button variant="ghost" size="sm" onClick={resetAll} className="text-sidebar-foreground/70 hover:text-sidebar-foreground">
            <RefreshCcw className="h-3.5 w-3.5 mr-2"/> Reset demo data
          </Button>)}
      </SidebarFooter>
    </Sidebar>);
}
