import ReadComponent from "../../component/products/ReadComponent";
import { useParams } from "react-router-dom";

const ReadPage = () => {
  const { pno } = useParams();
  return (
    <div className="p-4 w-full bg-white">
      <div className="text-3xl font-extrabold">Product Read Page</div>

      <ReadComponent pno={pno} />
    </div>
  );
};

export default ReadPage;
