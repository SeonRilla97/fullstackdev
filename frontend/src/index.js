import React from "react";
import ReactDOM from "react-dom/client";
import "./index.css";
import App from "./App";
import reportWebVitals from "./reportWebVitals";
import { Provider } from "react-redux";
import store from "./store";
const root = ReactDOM.createRoot(document.getElementById("root"));
root.render(
  // <React.StrictMode>
  //   <App />
  // </React.StrictMode>

  // Redux
  //Store 설정 완료 , 상태 데이터 관리를 위한 리듀서가 필요
  //Store : 창고
  //Reducer : 창고 지킴이


  <Provider store={store}>
      <App />
  </Provider>
);

reportWebVitals();
