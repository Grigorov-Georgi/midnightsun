import React from "react";
import { Menubar } from 'primereact/menubar';
import { InputText } from 'primereact/inputtext';

const Navbar = () => {
  const items = [
    {
      label: "Home",
      icon: "pi pi-fw pi-home",
    },
    {
      label: "Catalog",
      icon: "pi pi-fw pi-th-large",
      items: [
        {
          label: "All products",
          icon: "pi pi-fw pi-align-justify"
        }, 
        {
          label: "Add product",
          icon: "pi pi-fw pi-plus"
        },
        {
          label: "Edit product",
          icon: "pi pi-fw pi-refresh"
        }
      ]
    },
    {
      label: "Contact",
      icon: "pi pi-fw pi-envelope",
    },
    {
      label: "About",
      icon: "pi pi-fw pi-info-circle",
    },
    {
      label: "Shopping Cart",
      icon: "pi pi-fw pi-shopping-cart"
    },
    {
      label: "Login",
      icon: "pi pi-fw pi-user-plus"
    },
    {
      label: "Logout",
      icon: "pi pi-fw pi-user-minus"
    }
  ];

  const start = <img alt="logo" src="https://primefaces.org/cdn/primereact/images/logo.png" height="40" className="mr-2"></img>
  const end = <InputText placeholder="Search" type="text" className="w-full" />;

  return (
    <div className="card">
      <Menubar model={items} start={start} end={end}/>
    </div>
  );
};

export default Navbar;