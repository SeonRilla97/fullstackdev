import { useSearchParams } from "react-router-dom";
import { getAccessToken, getMemberWithAccessToken } from "../../api/kakaoApi";
import { useEffect } from "react";
import { useDispatch } from "react-redux";
import { login } from "../../slices/loginSlice";
import useCustomLogin from "./../../hooks/useCustomLogin";

const KakaoRedirectPage = () => {
  const [searchParams, setSearchParams] = useSearchParams();

  const dispatch = useDispatch();

  const { moveToPath } = useCustomLogin();
  const authCode = searchParams.get("code");

  //인가 코드 변경 시 AccessToken 다시 받아오기
  useEffect(() => {
    getAccessToken(authCode).then((accessToken) => {
      console.log(accessToken);

      getMemberWithAccessToken(accessToken).then((memberInfo) => {
        console.log("---------------");
        console.log(memberInfo);
        //쿠키에 저장
        dispatch(login(memberInfo));

        //소셜 회원이 아니라면
        if (memberInfo && !memberInfo.social) {
          console.log(`memberInfo : ${memberInfo}`);
          moveToPath("/");
        } else {
          moveToPath("/member/modify");
        }
      });
    });
  }, [authCode]);
  return (
    <div>
      <div>Kakao Login Redirect</div>
      <div>{authCode}</div>
    </div>
  );
};

export default KakaoRedirectPage;
