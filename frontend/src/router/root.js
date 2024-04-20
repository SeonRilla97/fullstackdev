// 필요한 순간까지 컴포넌트를 메모리상으로 올리지 않는다.
import { Suspense, lazy } from "react";
import productRouter from "./productRouter";
import memberRouter from "./memberRouter";
// 기본 라우팅 설정
const { createBrowserRouter } = require("react-router-dom");

// lazy : Router에서 미리 렌더링 하는게 아닌 해당 페이지가 필요할 때 렌더링을 수행한다. -> Suspense와 친구
const Loading = <div>Loading...</div>;
const Main = lazy(() => import("../pages/MainPage"));

const About = lazy(() => import("../pages/AboutPage"));

const ProductsIndex = lazy(() => import("../pages/products/IndexPage"));

const root = createBrowserRouter([
  {
    path: "/",
    element: (
      <Suspense fallback={Loading}>
        <Main />
      </Suspense>
    ),
  },
  {
    path: "/about",
    element: (
      <Suspense fallback={Loading}>
        <About />
      </Suspense>
    ),
  },
  {
    path: "/products",
    element: (
      <Suspense fallback={Loading}>
        <ProductsIndex />
      </Suspense>
    ),
    children: productRouter(),
  },
  {
    path: "/member",
    children: memberRouter(),
  },
]);

export default root;
