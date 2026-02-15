import { Activity, AlertTriangle, BarChart, Users } from 'lucide-react';

const StatCard = ({ title, value, icon: Icon, color }) => (
  <div className="bg-white p-6 rounded-xl shadow-sm border border-slate-100 flex items-center gap-4">
    <div className={`p-3 rounded-lg ${color}`}>
      <Icon className="h-6 w-6 text-white" />
    </div>
    <div>
      <p className="text-sm text-slate-500 font-medium">{title}</p>
      <h3 className="text-2xl font-bold text-slate-800">{value}</h3>
    </div>
  </div>
);

const AdminDashboard = () => {
  return (
    <div className="container mx-auto p-8">
      <div className="flex items-center justify-between mb-8">
        <div>
          <h2 className="text-2xl font-bold text-slate-800">Command Center</h2>
          <p className="text-slate-500">Live operational overview</p>
        </div>
        <button className="px-4 py-2 bg-slate-800 text-white rounded-lg hover:bg-slate-900 transition-colors">
          Download Report
        </button>
      </div>

      <div className="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-4 gap-6 mb-8">
        <StatCard title="Active Alerts" value="24" icon={AlertTriangle} color="bg-red-500" />
        <StatCard title="Rescues Pending" value="12" icon={Activity} color="bg-orange-500" />
        <StatCard title="Teams Deployed" value="8" icon={Users} color="bg-blue-500" />
        <StatCard title="Safe Zones" value="5" icon={BarChart} color="bg-green-500" />
      </div>

      <div className="grid grid-cols-1 lg:grid-cols-2 gap-6">
        <div className="bg-white p-6 rounded-xl shadow-sm border border-slate-100 h-64 flex items-center justify-center text-slate-400">
          Map Visualization Placeholder
        </div>
        <div className="bg-white p-6 rounded-xl shadow-sm border border-slate-100 h-64 flex items-center justify-center text-slate-400">
          Live Feed Placeholder
        </div>
      </div>
    </div>
  );
};

export default AdminDashboard;
