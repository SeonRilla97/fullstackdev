import axios from "axios";
 
//api Key
const rest_api_key = `e4d88587f3807d4521e376e2b33e4ac6`;
// Redirect URI
const redirect_uri = `http://172.30.1.90:3000/member/kakao`;

// Kakao Login Page URL
const auth_code_path = `https://kauth.kakao.com/oauth/authorize`;

// Kakao Login 인가 코드를 통해 Access Token 얻는 URL
const access_token_url = `https://kauth.kakao.com/oauth/token`;
export const getKakaoLoginLink = () => {
  const kakaoURL = `${auth_code_path}?response_type=code&client_id=${rest_api_key}&redirect_uri=${redirect_uri}`;

  return kakaoURL;
};

export const getAccessToken = async (authCode) => {
  const header = {
    headers: {
      "Content-Type": "application/x-www-form-urlencoded",
    },
  };

  const params = {
    grant_type: "authorization_code",
    client_id: rest_api_key,
    redirect_uri: redirect_uri,
    code: authCode,
  };

  const res = await axios.post(access_token_url, params, header);

  const accessToken = res.data.access_token;

  return accessToken;
};

export const getMemberWithAccessToken = async (accessToken) => {
  const res = await axios.get(
    `${API_SERVER_HOST}/api/member/kakao?accessToken=${accessToken}`
  );
  return res.data;
};
