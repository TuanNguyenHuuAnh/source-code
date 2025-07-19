import React from "react";
import Highlighter from "react-highlight-words";
import { removeAccents } from '../util/common';

const CustomSelectICDContainer = ({ data, keywork }) => {
  return (
    <div style={{ width: "100%", height: "26px" }}>
      {/* <div className="state">
        <span>
          <Highlighter
            className = "select-header"
            highlightClassName="simple-brown-small"
            searchWords={keywork.split(' ')}
            autoEscape={true}
            sanitize={removeAccents}
            textToHighlight={data.ICDCode}
          />
        </span>
      </div> */}
      <div className="hopital-name">
        <span>

          <Highlighter
            highlightClassName="simple-brown"
            searchWords={keywork.split(' ')}
            autoEscape={true}
            sanitize={removeAccents}
            textToHighlight={data.ICDName}
          />
        </span>

      </div>
    </div>
  );
};
export default CustomSelectICDContainer;


