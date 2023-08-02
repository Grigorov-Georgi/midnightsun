/* eslint-disable @typescript-eslint/no-explicit-any */
// import React, { useState, useEffect, useRef } from "react";
// import { InputText } from "primereact/inputtext";
// import { InputNumber } from "primereact/inputnumber";
// import { InputTextarea } from "primereact/inputtextarea";
// import { Dropdown } from "primereact/dropdown";
// import { Toast } from "primereact/toast";
// import { FileUpload } from "primereact/fileupload";
// import { getAllCategories } from "../services/CategoryService";

const ProductForm = () => {
  // const [product, setProduct] = useState({
  //   name: "",
  //   description: "",
  //   price: 0,
  //   category: null,
  // });

  // const [categories, setCategories] = useState([]);

  // const toast = useRef(null);

  // const handleSubmit = (event) => {
  //   event.preventDefault();
  // };

  // const handleInputChange = (event) => {
  //   const { name, value } = event.target;
  //   setProduct((prevProduct) => ({
  //     ...prevProduct,
  //     [name]: value,
  //   }));
  // };

  // const handleDropdownInputChange = (event) => {
  //   setProduct((prevProduct) => ({
  //     ...prevProduct,
  //     category: {
  //       id: event.value.id,
  //       name: event.value.name,
  //     },
  //   }));
  // };

  // const onUpload = (event) => {
  //   const file = event.files[0];
  //   const reader = new FileReader();

  //   reader.onloadend = () => {
  //     const base64String: any = reader?.result?.split(",")[1];
  //     setProduct((prevProduct) => ({
  //       ...prevProduct,
  //       image: base64String
  //     }));
  //   };

  //   if (file) {
  //     // Start reading the file as a data URL (base64)
  //     reader.readAsDataURL(file);
  //   }

  //   toast.current.show({
  //     severity: "info",
  //     summary: "Success",
  //     detail: "File Uploaded",
  //   });
  // };

  // useEffect(() => {
  //   console.log(product);
  // }, [product]);

  // useEffect(() => {
  //   getAllCategories()
  //     .then((res) => {
  //       setCategories(res.data.content);
  //     })
  //     .catch((error) => {
  //       console.error("Error fetching data:", error);
  //     });
  //   console.log(categories);
  // }, []);

  return (
    // <form onSubmit={handleSubmit}>
    //   <div className="card flex justify-content-center">
    //     <label className="font-bold block mb-2">Name:</label>
    //     <InputText
    //       value={product.name}
    //       name="name"
    //       onChange={handleInputChange}
    //     />
    //   </div>
    //   <div className="card flex justify-content-center">
    //     <label className="font-bold block mb-2">Price:</label>
    //     <InputNumber
    //       value={product.price}
    //       name="price"
    //       onValueChange={handleInputChange}
    //     />
    //     {/* <InputText
    //       value={product.price}
    //       name="price"
    //       onChange={handleInputChange}
    //     /> */}
    //   </div>
    //   <div className="card flex justify-content-center">
    //     <label className="font-bold block mb-2">Description:</label>
    //     <InputTextarea
    //       value={product.description}
    //       name="description"
    //       onChange={handleInputChange}
    //       rows={5}
    //       cols={80}
    //     />
    //     {/* <InputText
    //       value={product.description}
    //       name="description"
    //       onChange={handleInputChange}
    //     /> */}
    //   </div>
    //   <div className="card flex justify-content-center">
    //     <label className="font-bold block mb-2">Category:</label>
    //     <Dropdown
    //       value={product.category ? product.category.name : null}
    //       onChange={handleDropdownInputChange}
    //       options={categories}
    //       optionLabel="name"
    //       placeholder="Select a Category"
    //       className="w-full md:w-14rem"
    //     />
    //   </div>
    //   <div className="card flex justify-content-center">
    //     <label className="font-bold block mb-2">Category:</label>
    //     <Toast ref={toast}></Toast>
    //     <FileUpload
    //       mode="basic"
    //       name="demo[]"
    //       url="/api/upload"
    //       accept="image/*"
    //       maxFileSize={1000000}
    //       onUpload={onUpload}
    //     />
    //   </div>
    //   <input type="submit" value="Submit" />
    // </form>
    <div>Refactor the form component</div>
  );
};

export default ProductForm;
