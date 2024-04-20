import LogoutComponent from "../../component/member/LogoutComponent";
import BasicMenu from "../../component/menus/BasicMenu";

const LogoutPage = () => {
  return (
    <div className="fixed top-0 left-0 z-[1055] flex flex-col h-full w-full">
      <BasicMenu></BasicMenu>

      <div className="flex flex-wrap w-full h-full justify-center items-center border-2">
        <LogoutComponent />
      </div>
    </div>
  );
};

export default LogoutPage;
