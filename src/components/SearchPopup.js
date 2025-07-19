import React, {useState, useEffect, useRef} from 'react';
import '../common/Common.css';
import {
    CMS_LIST_DIST_META_KEYWORD,
    CMS_LIST_DIST_META_KEYWORD_DATA,
    CMS_NEWS,
    FE_BASE_URL,
    FROM_APP
} from '../constants';
import {getSession, removeAccents, setSession} from '../util/common';
import LoadingIndicator from '../common/LoadingIndicator2';
import useOnClickOutside from "./useOnClickOutside";
import {getListByPath} from "../util/APIUtils";

let clsMobile = 'popup__search_mb_article z-index-floating position-absolute';

function SearchPopup({closeBox, searchSubmit}) {
    const ref = useRef();
    const [keyword, setKeyword] = useState('');
    const [isShowPopup, setIsShowPopup] = useState(false);
    const [listDistMetaKeyword, setListDistMetaKeyword] = useState([]);

    const search = (event) => {
        event.preventDefault();
        if (document.getElementById('search-box-popup-id') && (document.getElementById('search-box-popup-id').className === "btn btn-primary disabled")) {
            return;
        }
        searchSubmit(keyword);
    }

    const closePopupEsc = (event) => {
        if (event.keyCode === 27) {
            closePopup();
        }

    }
    const closePopup = () => {
        closeBox();
    }

    const getMetaKeywordSuggestion = () => {
        if (getSession(CMS_LIST_DIST_META_KEYWORD_DATA)) {
            let listDistMetaKeyword = JSON.parse(getSession(CMS_LIST_DIST_META_KEYWORD_DATA));
            setListDistMetaKeyword(listDistMetaKeyword);
        } else {
            getListByPath(CMS_NEWS + CMS_LIST_DIST_META_KEYWORD )
                .then(res => {
                    if (res.statusCode === 200 && res.statusMessages === 'success' && res.data) {
                        setSession(CMS_LIST_DIST_META_KEYWORD_DATA, JSON.stringify(res.data));
                        setListDistMetaKeyword(res.data);
                    }
                }).catch(error => {
            });
        }

    }

    useOnClickOutside(ref, () => closeBox());

    useEffect(() => {
        if (getSession(FROM_APP)) {
            if ((window.location.pathname.split('/').length >= 5) || (window.location.pathname === '/timkiem/')) {
                clsMobile = 'popup__search_mb_article z-index-floating position-absolute';
                if (document.getElementById('search-popup-id')) {
                    document.getElementById('search-popup-id').className = clsMobile;
                }
            } else {
                clsMobile = 'popup__search_mb z-index-floating position-absolute';
                if (document.getElementById('search-popup-id')) {
                    document.getElementById('search-popup-id').className = clsMobile;
                }
            }
        } else {
            if (document.getElementById('search-popup-id')) {
                document.getElementById('search-popup-id').className = 'popup__search';
            }
        }

        if (document.getElementById('search-box-popup-id')) {
            if (keyword) {
                document.getElementById('search-box-popup-id').className = 'btn btn-primary';
                document.getElementById('search-box-popup-id').disabled = false;
            } else {
                document.getElementById('search-box-popup-id').className = 'btn btn-primary disabled';
                document.getElementById('search-box-popup-id').disabled = true;
            }

        }


        document.addEventListener("keydown", closePopupEsc, false);
        return () => {
            document.removeEventListener("keydown", closePopupEsc, false);
        }
    }, [keyword]);

    const filteredAndSortedKeywords = (keywords) => {
        const sortedKeywords = keywords.sort((a, b) => a.metaKeyword.localeCompare(b.metaKeyword));

        const normalizedKeyEdit = removeAccents(keyword.toLowerCase().trim());

        const filteredKeywords = sortedKeywords.filter(item => {
            const normalizedMetaKeyword = removeAccents(item.metaKeyword.toLowerCase(                                                                                                                                                                                   ).trim());
            return normalizedMetaKeyword.startsWith(normalizedKeyEdit);
        });
        return filteredKeywords.slice(0, 7);
    };

    useEffect(() => {
        getMetaKeywordSuggestion();
        const popupOpenAppContent = document.getElementsByClassName('popupOpenAppContent')[0];
        if (!popupOpenAppContent) {
            const popupSearch = document.getElementsByClassName('popup__search')[0];
            if (popupSearch) {
                popupSearch.style.marginTop = '0px';
            }
        }
    }, []);


    return (
        <div className="popup special search-popup show">
            <div className={!getSession(FROM_APP) ? "popup__search" : clsMobile} ref={ref} id="search-popup-id">
                <form className="formsearch" onSubmit={(event) => search(event)} autoComplete="off">
                    <div className="form-group">
                        <div className="formsearch-inline">
                            <div className="col-searchbar">
                                <div className="col-l">
                                    <div className='input' style={{minHeight: '48px', height: '48px'}}>
                                        <div className={keyword ? "input__content_expand" : "input__content"}>
                                            <input
                                                type="search"
                                                inputMode="search"
                                                className="form-control"
                                                placeholder="Nhập từ khóa để tìm kiếm bài viết"
                                                name="search-box-popup"
                                                value={keyword}
                                                onChange={(e) => {
                                                    setKeyword(e.target.value);
                                                    setIsShowPopup(true);
                                                }}
                                                // onFocus={() => setIsShowPopup(true)}
                                                onKeyPress={(e) => {
                                                    if (e.key === 'Enter') {
                                                        searchSubmit(keyword);
                                                    }
                                                }}
                                            />
                                            {keyword && isShowPopup && (
                                                <ul className="keyword-suggestions">
                                                    {listDistMetaKeyword && filteredAndSortedKeywords(listDistMetaKeyword)?.length === 0 ? (
                                                        <li>Không có từ khóa gợi ý tương ứng</li>
                                                    ) : (
                                                        listDistMetaKeyword && filteredAndSortedKeywords(listDistMetaKeyword)?.map((item, index) => (
                                                            <li
                                                                key={index}
                                                                onClick={() => {
                                                                    searchSubmit(item?.metaKeyword?.trim());
                                                                    setKeyword(item?.metaKeyword?.trim());
                                                                    setIsShowPopup(false);
                                                                }}
                                                            >
                                                                {item.metaKeyword}
                                                            </li>
                                                        ))
                                                    )}
                                                </ul>
                                            )}
                                        </div>
                                        {keyword ? (
                                            <></>
                                        ) : (
                                            <i className="icon" style={{cursor: 'pointer'}}
                                               onClick={() => setKeyword('')}>
                                                <img src={FE_BASE_URL + "/img/icon/Search.svg"} alt="search"/>
                                            </i>
                                        )}
                                    </div>
                                </div>

                                <div className="col-r">
                                    <button className="btn btn-primary disabled" style={{width: '175px'}}
                                            id="search-box-popup-id" disabled>Tìm kiếm
                                    </button>
                                </div>
                            </div>

                        </div>
                        <LoadingIndicator area="search-keywork"/>
                    </div>

                </form>
            </div>
            <div className={!getSession(FROM_APP) ? "popupbg_ext" : "popupbg_ext top-180"}></div>
        </div>
    )
}

export default SearchPopup;