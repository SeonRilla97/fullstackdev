import { createAsyncThunk, createSlice } from "@reduxjs/toolkit";
import { loginPost } from "../api/memberApi";
import { getCookie, removeCookie, setCookie } from "../util/cookieUtil";

const initState = {
  email: "",
};

const loadMemberCookie = () => {
  const memberInfo = getCookie("member");

  if (memberInfo && memberInfo.nickname) {
    memberInfo.nickname = decodeURIComponent(memberInfo.nickname);
  }

  return memberInfo;
};
// 비동기 함수 호출
export const loginPostAsync = createAsyncThunk("loginPostAsync", (param) => {
  return loginPost(param);
});

const loginSlice = createSlice({
  name: "LoginSlice",
  initialState: loadMemberCookie() || initState, //쿠키에 값이 없으면 초기값사용
  reducers: {
    login: (state, action) => {
      //Reducer의 구조가 필요할듯 -> action엔 payload에서 data를 보관중임
      console.log("login......");

      // {email , pw} 소셜 로그인 회원이 사용
      const payload = action.payload;

      console.log(`payload : ${payload}`);
      setCookie("member", JSON.stringify(payload), 1); // 1일
      // return { email: data.email };
      return payload;
    },
    logout: (state, action) => {
      console.log("logout......");

      removeCookie("member");
      //email이 빈 문자열로 만드는 로직 [한번도 initState가 변경된적은 없기때문]
      return { ...initState };
    },
  },
  extraReducers: (builder) => {
    // 비동기 함수 결과
    builder
      .addCase(loginPostAsync.fulfilled, (state, action) => {
        console.log("fulfilled"); // 완료
        //받아온 데이터를 저장
        const payload = action.payload;

        //로그인 성공 시
        if (!payload.error) {
          setCookie("member", JSON.stringify(payload), 1); // 1일 동안 유지되는 쿠키 셋팅
        }
        return payload;
      })
      .addCase(loginPostAsync.pending, (state, action) => {
        console.log("pending"); //처리중
      })
      .addCase(loginPostAsync.rejected, (state, action) => {
        console.log("rejected"); // 에러
        console.log(action);
      });
  },
});

export const { login, logout } = loginSlice.actions;

export default loginSlice.reducer;
