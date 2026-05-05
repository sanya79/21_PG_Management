import { Badge } from "@/components/ui/badge";
import { cn } from "@/lib/utils";
export function StatusBadge({ status, className }) {
    const s = status.toLowerCase();
    let cls = "bg-muted text-foreground border-border";
    if (["paid", "approved", "resolved"].includes(s))
        cls = "bg-success/15 text-success border-success/30";
    else if (["pending", "open"].includes(s))
        cls = "bg-warning/15 text-warning border-warning/40";
    else if (["denied"].includes(s))
        cls = "bg-destructive/15 text-destructive border-destructive/40";
    else if (["submitted", "in progress"].includes(s))
        cls = "bg-accent/15 text-accent border-accent/30";
    return (<Badge variant="outline" className={cn("font-medium capitalize", cls, className)}>
      {status}
    </Badge>);
}
export function PageHeader({ title, description, actions }) {
    return (<div className="flex flex-col sm:flex-row sm:items-center sm:justify-between gap-3 mb-6">
      <div>
        <h1 className="text-2xl font-bold tracking-tight">{title}</h1>
        {description && <p className="text-sm text-muted-foreground mt-1">{description}</p>}
      </div>
      {actions && <div className="flex gap-2">{actions}</div>}
    </div>);
}
