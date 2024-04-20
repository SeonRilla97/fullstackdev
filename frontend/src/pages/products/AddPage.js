import AddComponent from "../../component/products/AddComponent";
import productRouter from "./../../router/productRouter";

const AddPage = () => {
  return (
    <div className="p-4 w-full bg-white">
      <div className="text-3xl font-extrabold">products Add Page</div>
      <AddComponent />
    </div>
  );
};

export default AddPage;
