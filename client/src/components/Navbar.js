import React from "react";
import { Menubar } from 'primereact/menubar';
import './Navbar.css'

const Navbar = () => {
  const items = [
    {
      label: "Home",
      icon: "pi pi-fw pi-home",
    },
    {
      label: "About",
      icon: "pi pi-fw pi-info-circle",
    },
    {
      label: "Contact",
      icon: "pi pi-fw pi-envelope",
    },
  ];

  return (
    <div className="navbar">
      <Menubar model={items} />
    </div>
  );
};

export default Navbar;