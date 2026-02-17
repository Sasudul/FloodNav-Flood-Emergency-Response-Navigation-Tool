
const AdminDashboard = () => {
  return (
    <div className="p-6 space-y-6">
      <h1 className="text-3xl font-bold">Admin Dashboard</h1>

      {/* Stats Section */}
      <div className="p-4 border border-dashed border-gray-400 rounded bg-gray-50">
        <h2 className="text-xl font-semibold mb-2">Statistics</h2>
        <div className="h-24 bg-gray-200 flex items-center justify-center text-gray-500">
          [Stats Placeholder: Active Units, Pending Requests, etc.]
        </div>
      </div>

      <div className="grid grid-cols-1 md:grid-cols-2 gap-6">
        {/* Map Section */}
        <div className="p-4 border border-dashed border-gray-400 rounded bg-gray-50 min-h-[300px]">
          <h2 className="text-xl font-semibold mb-2">Live Map</h2>
          <div className="h-full bg-gray-200 flex items-center justify-center text-gray-500">
            [Map Component Placeholder]
          </div>
        </div>

        <div className="space-y-6">
          {/* SOS Requests List */}
          <div className="p-4 border border-dashed border-gray-400 rounded bg-gray-50">
            <h2 className="text-xl font-semibold mb-2">Incoming SOS Requests</h2>
            <div className="h-32 bg-gray-200 flex items-center justify-center text-gray-500">
              [List of SOS Requests Placeholder]
            </div>
          </div>

          {/* Road Block Form */}
          <div className="p-4 border border-dashed border-gray-400 rounded bg-gray-50">
            <h2 className="text-xl font-semibold mb-2">Report Road Blockage</h2>
            <div className="h-32 bg-gray-200 flex items-center justify-center text-gray-500">
              [Road Block Form Placeholder]
            </div>
          </div>
        </div>
      </div>
    </div>
  );
};

export default AdminDashboard;
