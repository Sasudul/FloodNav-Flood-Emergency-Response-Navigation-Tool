import { Navigation, Route } from 'lucide-react';

const RouteView = () => {
  return (
    <div className="container mx-auto p-8">
        <div className="flex items-center gap-4 mb-6">
          <div className="p-3 bg-blue-100 rounded-lg text-blue-600">
            <Route className="h-8 w-8" />
          </div>
          <div>
            <h2 className="text-2xl font-bold text-slate-800">Rescue Routes</h2>
            <p className="text-slate-500">Optimized navigation for emergency vehicles</p>
          </div>
        </div>

        <div className="bg-white rounded-lg shadow-lg overflow-hidden border border-slate-200">
            <div className="h-96 bg-slate-100 flex flex-col items-center justify-center text-slate-400">
                <Navigation className="h-16 w-16 mb-4 opacity-50" />
                <p>Interactive Map Component Loading...</p>
            </div>
            <div className="p-6 border-t border-slate-100">
                <h3 className="font-semibold text-lg mb-4">Route Details</h3>
                <div className="space-y-4">
                    <div className="flex items-start justify-between p-4 bg-slate-50 rounded-lg">
                        <div>
                            <span className="text-xs font-bold text-green-600 bg-green-100 px-2 py-1 rounded">FASTEST</span>
                            <h4 className="font-medium text-slate-800 mt-1">Route via Main Highway</h4>
                            <p className="text-sm text-slate-500">Usually 15 mins • Traffic light</p>
                        </div>
                        <button className="px-4 py-2 bg-blue-600 text-white text-sm font-medium rounded hover:bg-blue-700">
                            Start Navigation
                        </button>
                    </div>
                     <div className="flex items-start justify-between p-4 bg-slate-50 rounded-lg opacity-75">
                        <div>
                             <span className="text-xs font-bold text-orange-600 bg-orange-100 px-2 py-1 rounded">Flooded</span>
                            <h4 className="font-medium text-slate-800 mt-1">Route via Lowland Rd</h4>
                            <p className="text-sm text-slate-500">Blocked due to water level</p>
                        </div>
                        <button className="px-4 py-2 bg-slate-200 text-slate-500 text-sm font-medium rounded cursor-not-allowed" disabled>
                            Unavailable
                        </button>
                    </div>
                </div>
            </div>
        </div>
    </div>
  );
};

export default RouteView;
