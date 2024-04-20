import { useRef, useState } from "react";
import { postAdd } from "../../api/productApi";
import FetchingModal from "../common/FetchingModal";
import ResultModal from "../common/ResultModal";
import useCustomMove from "../../hooks/useCustomMove";
import {
  QueryClient,
  useMutation,
  useQueryClient,
} from "@tanstack/react-query";

const initState = {
  pname: "",
  pdesc: "",
  price: 0,
  files: [],
};

const AddComponent = () => {
  const [product, setProduct] = useState({ ...initState });
  const uploadRef = useRef();
  const { moveToList } = useCustomMove();

  const handleChangeProduct = (e) => {
    product[e.target.name] = e.target.value;
    setProduct({ ...product });
  };

  const addMutation = useMutation({
    mutationFn: (product) => postAdd(product),
  });

  const queryClient = useQueryClient();
  const handleClickAdd = (e) => {
    const files = uploadRef.current.files;
    const formData = new FormData();

    for (let i = 0; i < files.length; i++) {
      formData.append("files", files[i]);
    }

    formData.append("pname", product.pname);
    formData.append("pdesc", product.pdesc);
    formData.append("price", product.price);
    console.log("????");
    addMutation.mutate(formData);
  };

  const closeModal = () => {
    queryClient.invalidateQueries("products/list");
    moveToList({ page: 1 });
  };

  return (
    <div className="border-2 border-sky-200 mt-10 m-2 p-4">
      {addMutation.isPending ? <FetchingModal /> : <></>}

      {addMutation.isSuccess ? (
        <ResultModal
          title={"Product Add Result"}
          content={`${addMutation.data.RESULT}번 등록 완료`}
          callbackFn={closeModal}
        />
      ) : (
        <></>
      )}
      <div className="flex justify-center">
        <div className="relative mb-4 flex w-full flex-wrap items-stretch">
          <div className="w-1/5 p-6 text-right font-bold">Product Name</div>
          <input
            className="w-4/5 p-6 rounded-r  border border-solid border-neutral-300 shadow-md"
            type={"text"}
            name="pname"
            value={product.pname}
            onChange={handleChangeProduct}
          />
        </div>
      </div>

      <div className="flex justify-center">
        <div className="relative mb-4 flex w-full flex-wrap items-stretch">
          <div className="w-1/5 p-6 text-right font-bold">Desc</div>
          <textarea
            className="w-4/5 p-6 rounded-r  border border-solid border-neutral-300 shadow-md resize-y"
            name="pdesc"
            rows={4}
            value={product.pdesc}
            onChange={handleChangeProduct}
          >
            {product.pdesc}
          </textarea>
        </div>
      </div>

      <div className="flex justify-center">
        <div className="relative mb-4 flex w-full flex-wrap items-stretch">
          <div className="w-1/5 p-6 text-right font-bold">Price</div>
          <input
            className="w-4/5 p-6 rounded-r  border border-solid border-neutral-300 shadow-md"
            type={"number"}
            name="price"
            value={product.price}
            onChange={handleChangeProduct}
          />
        </div>
      </div>

      <div className="flex justify-center">
        <div className="relative mb-4 flex w-full flex-wrap items-stretch">
          <div className="w-1/5 p-6 text-right font-bold">Files</div>
          <input
            ref={uploadRef}
            className="w-4/5 p-6 rounded-r  border border-solid border-neutral-300 shadow-md"
            type={"file"}
            multiple={true}
          />
        </div>
      </div>

      <div className="flex justify-end">
        <div className="relative mb-4 flex p-4 flex-wrap items-stretch">
          <button
            className="rounded p-4 w-36 bg-blue-500 text-xl text-white"
            type="button"
            onClick={handleClickAdd}
          >
            ADD
          </button>
        </div>
      </div>
    </div>
  );
};

export default AddComponent;
