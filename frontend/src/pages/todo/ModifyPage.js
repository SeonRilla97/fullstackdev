import { useNavigate, useParams, useSearchParams } from "react-router-dom";
import ModifyComponent from "../../component/todo/ModifyComponent";

const ModifyPage = () => {
  const navigate = useNavigate();
  const { tno } = useParams();
  const moveToRead = () => {
    navigate({ pathname: `/todo/read/${tno}` });
  };

  const moveToList = () => {
    navigate({ pathname: `/todo/list` });
  };
  return (
    <div className="text-3xl w-full font-extrabold">
      <div className="text-3xl font-extrabold">Todo Modify Page</div>

      <ModifyComponent tno={tno} />
    </div>
  );
};

export default ModifyPage;
