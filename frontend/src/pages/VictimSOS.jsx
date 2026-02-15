import { MapPin, ShieldCheck } from 'lucide-react';

const VictimSOS = () => {
  return (
    <div className="container mx-auto p-8">
      <div className="bg-white rounded-lg shadow p-6 border border-slate-200">
        <div className="flex items-center gap-4 mb-6">
          <div className="p-3 bg-red-100 rounded-lg text-red-600">
            <MapPin className="h-8 w-8" />
          </div>
          <div>
            <h2 className="text-2xl font-bold text-slate-800">SOS Request Form</h2>
            <p className="text-slate-500">Submit emergency assistance request immediately</p>
          </div>
        </div>
        
        <div className="grid grid-cols-1 md:grid-cols-2 gap-6">
          <div className="space-y-4">
            <label className="block text-sm font-medium text-slate-700">Full Name</label>
            <input type="text" className="w-full p-2 border border-slate-300 rounded-md focus:ring-2 focus:ring-red-500 focus:border-red-500 outline-none" placeholder="John Doe" />
            
            <label className="block text-sm font-medium text-slate-700">Location Details</label>
            <textarea className="w-full p-2 border border-slate-300 rounded-md h-32 focus:ring-2 focus:ring-red-500 focus:border-red-500 outline-none" placeholder="Enter address or landmark..." />
            
            <button className="w-full py-3 bg-red-600 hover:bg-red-700 text-white font-bold rounded-lg transition-colors">
              SEND HELP NOW
            </button>
          </div>
          
          <div className="bg-slate-50 p-6 rounded-lg border border-slate-100">
            <h3 className="font-semibold text-slate-800 mb-4 flex items-center gap-2">
              <ShieldCheck className="h-5 w-5 text-green-600" />
              Safety Instructions
            </h3>
            <ul className="space-y-3 text-sm text-slate-600 list-disc pl-5">
              <li>Stay on high ground and avoid floodwaters.</li>
              <li>Keep your phone battery charged if possible.</li>
              <li>Prepare a go-bag with essentials.</li>
              <li>Wait for official rescue confirmation.</li>
            </ul>
          </div>
        </div>
      </div>
    </div>
  );
};

export default VictimSOS;
