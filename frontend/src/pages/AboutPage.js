import { Link } from "react-router-dom";
import BasicLayout from "../layout/BasicLayout";
import useCustomLogin from "../hooks/useCustomLogin";

const AboutPage = () => {
  const { isLogin, moveToLoginReturn } = useCustomLogin();

  if (!isLogin) {
    return moveToLoginReturn();
  }
  return (
    <BasicLayout>
      <div className="text-3xl">About Page</div>
    </BasicLayout>
  );
};
export default AboutPage;
