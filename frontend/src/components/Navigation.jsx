import { MapPin, Menu, Route as RouteIcon, ShieldAlert, X } from 'lucide-react';
import { useState } from 'react';
import { NavLink } from 'react-router-dom';

const Navigation = () => {
  const [isOpen, setIsOpen] = useState(false);

  const navItems = [
    { path: '/', label: 'SOS Request', icon: MapPin },
    { path: '/admin', label: 'Admin Dashboard', icon: ShieldAlert },
    { path: '/rescue', label: 'Rescue Routes', icon: RouteIcon },
  ];

  return (
    <nav className="bg-white shadow-lg border-b border-primary/10 sticky top-0 z-50">
      <div className="max-w-7xl mx-auto px-4 sm:px-6 lg:px-8">
        <div className="flex justify-between h-16">
          {/* Logo Section */}
          <div className="flex items-center">
            <div className="flex-shrink-0 flex items-center gap-3">
              <div className="bg-primary/10 p-2 rounded-lg">
                <ShieldAlert className="h-8 w-8 text-primary" />
              </div>
              <div>
                <h1 className="text-xl font-bold text-slate-800 tracking-tight">FloodNav</h1>
                <p className="text-xs text-slate-500 font-medium tracking-wide uppercase">Cyclone Ditwah Response</p>
              </div>
            </div>
          </div>

          {/* Desktop Menu */}
          <div className="hidden md:flex items-center space-x-4">
            {navItems.map(({ path, label, icon: Icon }) => (
              <NavLink
                key={path}
                to={path}
                className={({ isActive }) =>
                  `flex items-center gap-2 px-4 py-2 rounded-lg text-sm font-medium transition-all duration-200 ${
                    isActive
                      ? 'bg-primary text-white shadow-md shadow-primary/30'
                      : 'text-slate-600 hover:bg-slate-50 hover:text-primary'
                  }`
                }
              >
                <Icon className="w-4 h-4" />
                {label}
              </NavLink>
            ))}
          </div>

          {/* Mobile Menu Button */}
          <div className="md:hidden flex items-center">
            <button
              onClick={() => setIsOpen(!isOpen)}
              className="p-2 rounded-md text-slate-400 hover:text-slate-500 hover:bg-slate-100 focus:outline-none focus:ring-2 focus:ring-inset focus:ring-primary"
            >
              {isOpen ? <X className="h-6 w-6" /> : <Menu className="h-6 w-6" />}
            </button>
          </div>
        </div>
      </div>

      {/* Mobile Menu */}
      {isOpen && (
        <div className="md:hidden">
          <div className="px-2 pt-2 pb-3 space-y-1 sm:px-3 bg-white border-t border-slate-100">
            {navItems.map(({ path, label, icon: Icon }) => (
              <NavLink
                key={path}
                to={path}
                onClick={() => setIsOpen(false)}
                className={({ isActive }) =>
                    `flex items-center gap-3 px-3 py-3 rounded-md text-base font-medium transition-colors ${
                      isActive
                        ? 'bg-primary/5 text-primary border-l-4 border-primary'
                        : 'text-slate-600 hover:bg-slate-50 hover:text-slate-900'
                    }`
                }
              >
                <Icon className="w-5 h-5" />
                {label}
              </NavLink>
            ))}
          </div>
        </div>
      )}
    </nav>
  );
};

export default Navigation;
