import { useEffect, useState } from "react";
import { getList } from "../../api/todoApi";
import useCustomMove from "../../hooks/useCustomMove";
import PageComponent from "./../common/PageComponent";

const initState = {
  dtoList: [],
  pageNumList: [],
  pageRequestDTO: null,
  prev: false,
  next: false,
  totalCount: 0,
  prevPage: 0,
  netxPage: 0,
  totalPage: 0,
  current: 0,
};

const ListComponent = () => {
  const { page, size, moveToList, refresh, moveToRead } = useCustomMove();

  // 상태 관리 -> set을 통해 랜더링이 다시 발생됨 [ 변수는 이걸로 선언 ]
  const [serverDate, setServerData] = useState(initState);

  // side Effect 처리 : [ 타이머 / 외부와 통신 ] , 렌더링 이후에 동작하는 함수라는것만 기억

  useEffect(() => {
    //Side Effect : 서버와의 통신을 위해 useEffect 사용
    getList({ page, size }).then((data) => {
      console.log(data);
      setServerData(data);
    });
  }, [page, size, refresh]);

  return (
    <div className="border-2 border-blue-100 mt-10 mr-2 ml-2">
      <div className="flex flex-wrap mx-auto justify-center">
        {serverDate.dtoList.map((todo) => (
          <div
            key={todo.tno}
            className="w-full min-w-[400px] p-2 m-2 rounded shadow-md"
            onClick={() => moveToRead(todo.tno)}
          >
            <div className="flex">
              <div className="font-extrabold text-2xl p-2 w-1/12">
                {todo.tno}
              </div>
              <div className="text-1xl m-1 p-2 w-8/12 font-extrabold">
                {todo.title}
              </div>
              <div className="text-1xl m-1 p-2 w-8/12 font-medium">
                {todo.dueDate}
              </div>
            </div>
          </div>
        ))}
      </div>
      <PageComponent serverData={serverDate} movePage={moveToList} />
    </div>
  );
};
export default ListComponent;
