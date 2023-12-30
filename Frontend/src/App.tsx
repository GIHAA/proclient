import React from 'react';
import Home from './pages/Home';
import { ToastContainer, toast } from 'react-toastify';
import 'react-toastify/dist/ReactToastify.css';

const App = () => {
  return (
    <div className="App dark:bg-gray-900">
      <Home />
      <ToastContainer />
    </div>
  );
};

export default App;
