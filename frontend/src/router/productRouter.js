import { Suspense, lazy } from "react";
import { Navigate } from "react-router-dom";

const Loading = <div>Loading...</div>;
const ProductList = lazy(() => import("../pages/products/ListPage"));
const ProductsAdd = lazy(() => import("../pages/products/AddPage"));
const ProductRead = lazy(() => import("../pages/products/ReadPage"));
const ProductModify = lazy(() => import("../pages/products/ModifyPage"));
const productRouter = () => {
  return [
    {
      path: "list",
      element: (
        <Suspense fallback={Loading}>
          <ProductList></ProductList>
        </Suspense>
      ),
    },
    {
      path: "",
      element: <Navigate replace to="list"></Navigate>,
    },
    {
      path: "add",
      element: (
        <Suspense fallback={Loading}>
          <ProductsAdd></ProductsAdd>
        </Suspense>
      ),
    },
    {
      path: "read/:pno",
      element: (
        <Suspense fallback={Loading}>
          <ProductRead></ProductRead>
        </Suspense>
      ),
    },
    {
      path: "modify/:pno",
      element: (
        <Suspense fallback={Loading}>
          <ProductModify></ProductModify>
        </Suspense>
      ),
    },
  ];
};

export default productRouter;
