import { useEffect, useState } from "react";
import { getOne } from "../../api/todoApi";
import useCustomMove from "../../hooks/useCustomMove";

const initState = {
  tno: 0,
  title: "",
  writer: "",
  dueDate: null,
  complete: false,
};

const ReadComponent = ({ tno }) => {
  //서버로부터 가져온 데이터를  저장 (todo)
  const [todo, setTodo] = useState(initState);
  const { moveToList, moveToModify } = useCustomMove();

  useEffect(() => {
    getOne(tno).then((data) => {
      console.log(data);
      setTodo(data);
    });
  }, [tno]);

  return (
    <div>
      {makeDiv("Tno", todo.tno)}
      {makeDiv("Writer", todo.writer)}
      {makeDiv("Title", todo.title)}
      {makeDiv("Due Date", todo.dueDate)}
      {makeDiv("Complete", todo.complete ? "Completed" : "Not Yet")}

      <div className="flex justify-end p-4">
        <button
          type="button"
          className="rounded p-4 m-2 text-xl w-32 text-white bg-blue-500"
          onClick={() => moveToList()} //이 값이 리스트의 상태값을 유지하는지 확인해야함 (size / page)
        >
          List
        </button>

        <button
          type="button"
          className="rounded p-4 m-2 text-xl w-32 text-white bg-blue-500"
          onClick={() => moveToModify(tno)} //이 값이 리스트의 상태값을 유지하는지 확인해야함 (size / page)
        >
          Modify
        </button>
      </div>
    </div>
  );
};

const makeDiv = (title, value) => (
  <div className="flex justify-center">
    <div className="relative mb-4 flex w-full flex-wrap items-stretch">
      <div className="w-1/5 p-6 text-right font-bold">{title}</div>
      <div className="w-4/5 p-6 rounded-r border border-solid shadow-md">
        {value}
      </div>
    </div>
  </div>
);

export default ReadComponent;
