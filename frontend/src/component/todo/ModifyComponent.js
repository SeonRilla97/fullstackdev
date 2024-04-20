import { useEffect, useState } from "react";
import { deleteOne, getOne, putOne } from "../../api/todoApi";
import useCustomMove from "../../hooks/useCustomMove";
import ResultModal from "../common/ResultModal";

const initState = {
  tno: 0,
  title: "",
  writer: "",
  dueDate: null,
  complete: false,
};

const ModifyComponent = ({ tno, moveList, moveRead }) => {
  const [todo, setTodo] = useState({ ...initState });

  const [result, setResult] = useState(null);

  const { moveToList, moveToRead } = useCustomMove();
  useEffect(
    () => {
      getOne(tno).then((data) => setTodo(data));
    },
    { tno }
  );

  const handleChangeTodo = (e) => {
    todo[e.target.name] = e.target.value;

    setTodo({ ...todo });
  };

  const handleChangeTodoComplete = (e) => {
    const value = e.target.value;

    todo.complete = value === "Y";
    setTodo({ ...todo });
  };

  const handleClickModify = () => {
    //버튼 클릭시
    putOne(todo).then((data) => {
      // console.log("modify result : " + data);
      setResult("Modify");
    });
  };

  const handleClickDelete = () => {
    deleteOne(tno).then((data) => {
      // console.log("delete result : " + data);
      setResult("Deleted");
    });
  };

  const closeModal = () => {
    if (result === "Deleted") {
      moveToList();
    } else {
      moveToRead(tno);
    }
  };

  const makeDiv = (title, value, isUpdate, type) => {
    return (
      <div className="flex justify-center">
        <div className="relative mb-4 flex w-full flex-wrap items-stretch">
          <div className="w-1/5 p-6 text-right font-bold">{title}</div>
          {isUpdate ? (
            // 수정 가능 항목
            <input
              type={type}
              name={title}
              value={value}
              onChange={handleChangeTodo}
              className="w-4/5 p-6 rounded-r border border-solid shadow-md border-neutral-300"
            />
          ) : (
            // 수정 불가 항목
            <div className="w-4/5 p-6 rounded-r border border-solid shadow-md bg-gray-100">
              {value}
            </div>
          )}
        </div>
      </div>
    );
  };

  return (
    <div className="border-2 border-sky-200 mt-10 m-2 p-4">
      {result ? (
        <ResultModal
          title={"처리결과"}
          content={result}
          callbackFn={closeModal}
        ></ResultModal>
      ) : (
        <></>
      )}
      {makeDiv("Tno", todo.tno)}
      {makeDiv("Writer", todo.writer)}
      {makeDiv("title", todo.title, true, "text")}
      {makeDiv("duedate", todo.dueDate, true, "date")}

      <div className="flex justify-center">
        <div className="relative mb-4 flex w-full flex-wrap items-stretch">
          <div className="w-1/5 p-6 text-right font-bold">COMPLETE</div>
          <select
            className="border-solid border-2 rounded m-1 p-2"
            onChange={handleChangeTodoComplete}
            value={todo.complete ? "Y" : "N"}
          >
            <option value="Y">Completed</option>
            <option value="N">Not Yet</option>
          </select>
        </div>
      </div>

      <div className="flex justify-end">
        <button
          className="rounded p-4 m-2 text-xl w-32 text-white bg-blue-500"
          onClick={handleClickDelete}
        >
          DELETE
        </button>
        <button
          className="rounded p-4 m-2 text-xl w-32 text-white bg-blue-500"
          onClick={handleClickModify}
        >
          MODIFY
        </button>
      </div>
    </div>
  );
};

export default ModifyComponent;
