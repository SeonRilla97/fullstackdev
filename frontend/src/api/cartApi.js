import jwtAxios from "../util/jwtUtil";
 //로그인 정보를 사용하기 때문에 jwtAxios 사용
const host = `${process.env.REACT_APP_SERVER_HOST}/api/cart`;

export const getCartItems = async () => {
  const res = await jwtAxios.get(`${host}/items`);

  return res.data;
};

export const postChangeCart = async (cartItem) => {
  const res = await jwtAxios.post(`${host}/change`, cartItem);

  return res.data;
};
