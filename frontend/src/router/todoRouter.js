// 필요한 순간까지 컴포넌트를 메모리상으로 올리지 않는다.
import { Suspense, lazy } from "react";
import { Navigate } from "react-router-dom";

const Loading = <div>Loading...</div>;
const TodoList = lazy(() => import("../pages/todo/ListPage"));
const TodoRead = lazy(() => import("../pages/todo/ReadPage"));
const TodoAdd = lazy(() => import("../pages/todo/AddPage"));
const TodoModify = lazy(() => import("../pages/todo/ModifyPage"));

const todoRouter = () => {
  return [
    {
      path: "list",
      element: (
        <Suspense fallback={Loading}>
          <TodoList></TodoList>
        </Suspense>
      ),
    },
    {
      path: "", // ""으로 넘어오면 list로 Redirect 시킨다 [ outlet을 사용할 때 ]
      element: <Navigate replace to="list"></Navigate>,
    },
    {
      path: "read/:tno",
      element: (
        <Suspense fallback={Loading}>
          <TodoRead></TodoRead>
        </Suspense>
      ),
    },
    {
      path: "add",
      element: (
        <Suspense fallback={Loading}>
          <TodoAdd></TodoAdd>
        </Suspense>
      ),
    },
    {
      path: "modify/:tno",
      element: (
        <Suspense fallback={Loading}>
          <TodoModify></TodoModify>
        </Suspense>
      ),
    },
  ];
};

export default todoRouter;
