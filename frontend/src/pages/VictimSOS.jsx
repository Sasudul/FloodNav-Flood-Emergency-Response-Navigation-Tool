
const VictimSOS = () => {
  return (
    <div className="p-6 max-w-4xl mx-auto space-y-6">
      <h1 className="text-3xl font-bold text-red-600">Emergency SOS Request</h1>

      <div className="grid grid-cols-1 md:grid-cols-2 gap-8">
        {/* Form Section */}
        <div className="space-y-4 p-6 border border-gray-200 rounded-lg shadow-sm bg-white">
          <h2 className="text-xl font-semibold">Request Assistance</h2>
          
          <div className="space-y-2">
            <label className="block text-sm font-medium">Location</label>
            <input type="text" disabled className="w-full p-2 border border-gray-300 rounded bg-gray-50" placeholder="[Location Input Placeholder]" />
          </div>

          <div className="space-y-2">
            <label className="block text-sm font-medium">Number of Affected People</label>
            <input type="number" disabled className="w-full p-2 border border-gray-300 rounded bg-gray-50" placeholder="[Count Placeholder]" />
          </div>

          <div className="space-y-2">
            <label className="block text-sm font-medium">Severity</label>
            <select disabled className="w-full p-2 border border-gray-300 rounded bg-gray-50">
              <option>[Severity Select Placeholder]</option>
            </select>
          </div>

          <div className="space-y-2">
            <label className="block text-sm font-medium">Additional Notes</label>
            <textarea disabled className="w-full p-2 border border-gray-300 rounded bg-gray-50 h-24" placeholder="[Notes Textarea Placeholder]"></textarea>
          </div>

          <button disabled className="w-full py-3 bg-red-600 text-white font-bold rounded opacity-50 cursor-not-allowed">
            SUBMIT SOS
          </button>
        </div>

        {/* Map & Info Section */}
        <div className="space-y-6">
          <div className="p-4 border border-dashed border-gray-400 rounded bg-gray-50 h-64">
            <h2 className="text-lg font-semibold mb-2">Location Confirmation</h2>
            <div className="h-full bg-gray-200 flex items-center justify-center text-gray-500">
              [Pins & Map Placeholder]
            </div>
          </div>

          <div className="p-4 bg-blue-50 border border-blue-100 rounded text-blue-800">
            <h2 className="text-lg font-semibold mb-2">Important Info</h2>
            <div className="text-sm">
              [Info Box Placeholder: Safety instructions, emergency numbers, etc.]
            </div>
          </div>
        </div>
      </div>
    </div>
  );
};

export default VictimSOS;
