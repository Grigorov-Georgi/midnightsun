import { InputText } from "primereact/inputtext";
import { InputNumber } from "primereact/inputnumber";
import { InputTextarea } from "primereact/inputtextarea";
import { Dropdown } from "primereact/dropdown";
import { Button } from "primereact/button";
import { Toast } from "primereact/toast";
import { FileUpload, FileUploadUploadEvent } from "primereact/fileupload";
import styles from "./NewProductForm.module.css";
import { useRef, useState } from "react";

const dummyProductOptions = ["option 1", "option 2", "option 3", "option 4"];

export const NewProductForm = () => {
  const [name, setName] = useState<string>("");
  const [price, setPrice] = useState<number | null>(null);
  const [description, setDescription] = useState<string>("");
  const [selectedOption, setSelectedOption] = useState<string>("");
  const toastRef = useRef<Toast>(null);

  const handleSubmit = () => {
    console.log("Perfom REST call");
  };

  const handleUpload = (event: FileUploadUploadEvent) => {
    // TODO -> Discuss and handle the file upload logic + troubleshoot and implement the toast component
    console.log(event);
    if (toastRef.current) {
      toastRef.current.show({
        severity: "info",
        summary: "Success",
        detail: "File Uploaded",
      });
    }
  };

  return (
    <div className={styles.form}>
      <Toast ref={toastRef} style={{ zIndex: "1000000" }} position="top-left" />
      <h2 style={{ color: "white" }} className={styles.formItem}>
        Create a new product
      </h2>
      <InputText
        placeholder={"Enter product name..."}
        value={name}
        onChange={(e) => setName(e.target.value)}
        className={styles.formItem}
      />
      <InputNumber
        placeholder={"Enter product price..."}
        maxFractionDigits={2}
        mode={"currency"}
        currency={"EUR"}
        value={price}
        onValueChange={(e) => setPrice(e.value as number)}
        className={styles.formItem}
      />
      <InputTextarea
        placeholder={"Enter product description..."}
        rows={10}
        cols={50}
        onChange={(e) => setDescription(e.target.value)}
        value={description}
        className={styles.formItem}
      />
      <div className={styles.miscContainer}>
        <Dropdown
          options={dummyProductOptions}
          value={selectedOption}
          onChange={(e) => setSelectedOption(e.value)}
          placeholder={"Choose an option..."}
          style={{ marginRight: "15px" }}
        />
        <FileUpload
          mode="basic"
          accept="image/*"
          maxFileSize={1000000}
          onUpload={(ev) => handleUpload(ev)}
        />
      </div>
      <Button
        label={"Submit"}
        className={styles.submitBtn}
        onClick={handleSubmit}
      />
    </div>
  );
};
