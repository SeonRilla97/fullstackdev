import axios from "axios";
import { getCookie, setCookie } from "./cookieUtil";
import { API_SERVER_HOST } from "./../api/todoApi";
/*서버로부터 요청전, 응답 후 인터셉터 하여 추가 작업 하는 경우 필요 */
const jwtAxios = axios.create();

//silent refresh (Refresh Token 을 이용한 자동 AccessToken 재발급)
const refreshJWT = async (accessToken, refreshToken) => {
  const host = API_SERVER_HOST;
  const header = { headers: { Authorization: `Bearer ${accessToken}` } };

  const res = await axios.get(
    `${host}/api/member/refresh?refreshToken=${refreshToken}`,
    header
  );
  console.log("-------------------");
  console.log(res.data);

  return res.data;
};
//before request
const beforeReq = (config) => {
  console.log("before request ............");

  const memberInfo = getCookie("member");

  if (!memberInfo) {
    console.log("Member NOT FOUND");
    return Promise.reject({
      response: {
        data: { error: "REQUIRE_LOGIN" },
      },
    });
  }
  const { accessToken } = memberInfo;

  config.headers.Authorization = `Bearer ${accessToken}`;
  return config;
};

//fail request
const requestFail = (err) => {
  console.log("request error.........");

  return Promise.reject(err);
};

//before return response
const beforeRes = async (res) => {
  console.log("before return response ........");

  console.log(res);
  const data = res.data;

  //silent Refresh
  if (data && data.error === "ERROR_ACCESS_TOKEN") {
    const memberCookieValue = getCookie("member");

    const result = await refreshJWT(
      memberCookieValue.accessToken,
      memberCookieValue.refreshToken
    );

    console.log("refreshJWT RESULT", result);

    memberCookieValue.accessToken = result.accessToken;
    memberCookieValue.refreshToken = result.refreshToken;

    setCookie("member", JSON.stringify(memberCookieValue), 1);

    //원래 하려했던 요청 보내기
    const originalRequest = res.config;

    originalRequest.headers.Authorization = `Bearer ${result.accessToken}`;

    return res;
  }
  return res;
};

//fail response
const responseFail = (err) => {
  console.log("response fail error...........");
  return Promise.reject(err);
};

//요청 인터셉터
jwtAxios.interceptors.request.use(beforeReq, requestFail);
//응답 인터셉터
jwtAxios.interceptors.response.use(beforeRes, responseFail);

export default jwtAxios;
