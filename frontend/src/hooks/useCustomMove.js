// Custom Hook의 파일명은 use로 시작해야한다.

import { useState } from "react";
import {
  createSearchParams,
  useNavigate,
  useSearchParams,
} from "react-router-dom";

const getNum = (param, defaultValue) => {
  if (!param) {
    return defaultValue;
  }

  return parseInt(param);
};

const useCustomMove = () => {
  //Page 이동 시 사용
  const navigate = useNavigate();

  const [refresh, setRefresh] = useState(false);
  // useLocation : 현재 URL
  // useSearchParams : QueryString -> {Query Param || Path Variable} 가져오기
  const [queryParams] = useSearchParams();

  //page, size 값 가져오기
  const page = getNum(queryParams.get("page"), 1);
  const size = getNum(queryParams.get("size"), 10);

  const queryDefault = createSearchParams({ page, size }).toString();

  //list페이지로 이동 [ 기존의 Paging 정보를 유지하면서 이동 ]
  const moveToList = (pageParam) => {
    let queryStr = "";

    if (pageParam) {
      const pageNum = getNum(pageParam.page, 1);
      const sizeNum = getNum(pageParam.size, 10);

      queryStr = createSearchParams({
        page: pageNum,
        size: sizeNum,
      }).toString();
    } else {
      queryStr = queryDefault;
    }

    // 페이지 리스트의 현재 페이지를 눌렀을 때 서버로부터 다시 데이터를 불러오도록
    setRefresh(!refresh);

    navigate({ pathname: `../list`, search: queryStr });
  };

  const moveToModify = (num) => {
    console.log(queryDefault);

    navigate({
      pathname: `../modify/${num}`,
      search: queryDefault, // 수정 시 Query String 유지 위함
    });
  };

  const moveToRead = (num) => {
    console.log(queryDefault);

    navigate({
      pathname: `../read/${num}`,
      search: queryDefault, // 수정 시 Query String 유지 위함
    });
  };

  return { moveToList, moveToModify, moveToRead, page, size, refresh };
};

export default useCustomMove;
