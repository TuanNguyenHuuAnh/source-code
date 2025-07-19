/* eslint-disable react/no-array-index-key */
/* eslint-disable no-nested-ternary */
import React, {useState, useEffect, useRef} from 'react';

export default function Lyrics(props) {
    let {isLyricsVisible, lyrics, mode} = props;
    lyrics = JSON.parse(lyrics);
    // const lyrics = [{
    //     "Id": "TVTC",
    //     "Text": "Chào Anh, Em xin phép ghi âm/ghi hình cuộc trao đổi này để Công ty bảo hiểm nhân thọ Dai-ichi Việt Nam và Ngân hàng Sacombank lưu trữ nhé.",
    //     "QuestionNo": "1",
    //     "SectionNo": "2",
    //     "Keywords": ["Anh", "Em", "Ngân hàng Sacombank"]
    // }, {
    //     "Id": "KH", "Text": "Đồng ý.", "QuestionNo": "2", "SectionNo": "2", "Keywords": []
    // }, {
    //     "Id": "TVTC",
    //     "Text": "Em tên là Phạm Thị Phương Dung mã số tư vấn là 678688.\n Em xin xác nhận lại các nội dung quan trọng đã tư vấn về Hợp đồng Bảo hiểm Chương trình bảo hiểm An Tâm Hạnh Phúc- Sản phẩm bảo hiểm Liên kết chung An Tâm Song Hành theo Yêu cầu bảo hiểm số EA00974076 của Anh.",
    //     "QuestionNo": "3",
    //     "SectionNo": "3",
    //     "Keywords": ["Em", "Phạm Thị Phương Dung", "678688", "Em", "Chương trình bảo hiểm An Tâm Hạnh Phúc- Sản phẩm bảo hiểm Liên kết chung An Tâm Song Hành", "EA00974076", "Anh"]
    // }, {
    //     "Id": "KH", "Text": "Đồng ý.", "QuestionNo": "4", "SectionNo": "3", "Keywords": []
    // }, {
    //     "Id": "TVTC",
    //     "Text": "Bên mua bảo hiểm là Anh PHẠM VĂN SỬ; sinh năm 2006; có địa chỉ liên hệ tại sad, Phước Tỉnh, Long Đất, Bà Rịa - Vũng Tàu, số điện thoại 066 5445 555, đúng không Anh?",
    //     "QuestionNo": "5",
    //     "SectionNo": "4",
    //     "Keywords": ["Anh", "PHẠM VĂN SỬ", "2006", "sad, Phước Tỉnh, Long Đất, Bà Rịa - Vũng Tàu", "066 5445 555", "Anh"]
    // }, {
    //     "Id": "KH", "Text": "Đúng vậy.", "QuestionNo": "6", "SectionNo": "4", "Keywords": []
    // }, {
    //     "Id": "TVTC",
    //     "Text": "Anh là Bên mua bảo hiểm cũng chính là người được bảo hiểm, đúng không Anh?",
    //     "QuestionNo": "7",
    //     "SectionNo": "5",
    //     "Keywords": ["Anh", "Anh"]
    // }, {
    //     "Id": "KH", "Text": "Đúng vậy", "QuestionNo": "8", "SectionNo": "5", "Keywords": []
    // }, {
    //     "Id": "TVTC",
    //     "Text": "Hồ sơ của Anh có Số tiền bảo hiểm của sản phẩm chính là 7,000,000,000 đồng, với Kế hoạch đóng phí dự kiến trên bảng minh họa là 15 năm; mức phí dự tính đóng là 83,909,000 đồng, định kỳ đóng phí Năm, là phù hợp với khả năng tài chính của Anh?",
    //     "QuestionNo": "9",
    //     "SectionNo": "6",
    //     "Keywords": ["Anh", "7,000,000,000", "15", "83,909,000", "Năm", "Anh"]
    // }, {
    //     "Id": "KH", "Text": "Đúng vậy.", "QuestionNo": "10", "SectionNo": "6", "Keywords": []
    // }, {
    //     "Id": "TVTC",
    //     "Text": "Anh đã được Em tư vấn và giải thích đầy đủ các nội dung tại quy tắc Điều khoản bảo hiểm của sản phẩm này cụ thể như:\n - Các quyền lợi bảo hiểm, điều kiện để nhận các quyền lợi bảo hiểm \n - Các khoản phí mà Công ty đã tính cho Anh và \n - Các Điều khoản loại trừ trách nhiệm bảo hiểm.",
    //     "QuestionNo": "11",
    //     "SectionNo": "7",
    //     "Keywords": ["Anh", "Em", "Anh"]
    // }, {
    //     "Id": "KH", "Text": "Đúng vậy.", "QuestionNo": "12", "SectionNo": "7", "Keywords": []
    // }, {
    //     "Id": "TVTC",
    //     "Text": "Anh cũng được tư vấn và giải thích đầy đủ về quyền lợi Đầu tư khi tham gia sản phẩm bảo hiểm này. Kết quả đầu tư thực tế có thể cao hơn hoặc thấp hơn giá trị trong bảng minh họa. Anh có thể theo dõi Giá trị tài khoản trong ứng dụng Dai-ichi Connect.",
    //     "QuestionNo": "13",
    //     "SectionNo": "7",
    //     "Keywords": ["Anh", "Anh"]
    // }, {
    //     "Id": "KH",
    //     "Text": "Anh xác nhận đã được tư vấn và giải thích đầy đủ.",
    //     "QuestionNo": "14",
    //     "SectionNo": "7",
    //     "Keywords": ["Anh"]
    // }, {
    //     "Id": "TVTC",
    //     "Text": "Khi tham gia bảo hiểm, Anh có các quyền như sau:\n - Quyền được yêu cầu giải thích về Hợp đồng bảo hiểm, nhận bộ Hợp đồng bảo hiểm.\n - Quyền được xem xét lại Hợp đồng bảo hiểm trong vòng 21 ngày kể từ ngày nhận bộ Hợp đồng. Trong thời hạn này Anh có thể yêu cầu hủy Hợp đồng và nhận lại phí bảo hiểm sau khi trừ chi phí kiểm tra y tế (nếu có).\n - Sau thời hạn này, nếu Anh yêu cầu hủy Hợp đồng thì chỉ nhận được giá trị hoàn lại theo quy định tại Quy tắc Điều khoản (nếu có).\n Đồng thời, nghĩa vụ cơ bản của Anh là:\n - Kê khai chính xác và đầy đủ các thông tin, đặc biệt là thông tin sức khỏe của Người được bảo hiểm trong Yêu cầu bảo hiểm số EA00974076 và bất kỳ thông tin, tài liệu nào cung cấp cho Công ty bảo hiểm nhân thọ Dai-ichi Việt Nam.\n - Đóng phí bảo hiểm đầy đủ và đúng hạn.",
    //     "QuestionNo": "15",
    //     "SectionNo": "8",
    //     "Keywords": ["Anh", "Anh", "Anh", "Anh", "EA00974076"]
    // }, {
    //     "Id": "KH",
    //     "Text": "Anh xác nhận đã hiểu rõ các quyền và nghĩa vụ của mình.",
    //     "QuestionNo": "16",
    //     "SectionNo": "8",
    //     "Keywords": ["Anh"]
    // }, {
    //     "Id": "TVTC",
    //     "Text": "Đây là sản phẩm bảo hiểm của Dai-ichi được phân phối qua Ngân hàng.\n Anh vui lòng xác nhận giúp việc tham gia bảo hiểm trên cơ sở tự nguyện, phù hợp với nhu cầu tài chính và bảo hiểm của mình đúng không Anh?",
    //     "QuestionNo": "17",
    //     "SectionNo": "9",
    //     "Keywords": ["Anh", "Anh"]
    // }, {
    //     "Id": "KH", "Text": "Đúng vậy.", "QuestionNo": "18", "SectionNo": "9", "Keywords": []
    // }, {
    //     "Id": "TVTC",
    //     "Text": "Vậy Em xin phép dừng cuộc trao đổi tại đây. Em cảm ơn Anh.",
    //     "QuestionNo": "19",
    //     "SectionNo": "10",
    //     "Keywords": ["Em", "Em", "Anh"]
    // }];

    const [currentLyricIndex, setCurrentLyricIndex] = useState(0);
    const [currentWordIndex, setCurrentWordIndex] = useState(0);
    const [currentColor, setCurrentColor] = useState('black');
    const [colorMap, setColorMap] = useState([]);
    const [lyricsHeight, setLyricsHeight] = useState(0);
    const [currentLyricId, setCurrentLyricId] = useState('TVTC');

    const lyricsRef = useRef(null);

    const setColor = (lyricIndex, wordIndex, color) => {
        setColorMap(prevColorMap => {
            const newColorMap = [...prevColorMap];
            if (!newColorMap[lyricIndex]) {
                newColorMap[lyricIndex] = [];
            }
            newColorMap[lyricIndex][wordIndex] = color;
            return newColorMap;
        });
    };

    const scrollLyrics = () => {
        const lyricsContainer = lyricsRef.current;
        if (lyricsContainer) {
            const maxScrollTop = lyricsContainer.scrollHeight - lyricsContainer.clientHeight;
            if (lyricsContainer.scrollTop < maxScrollTop) {
                lyricsContainer.scrollTop += lyricsHeight;
            }
        }
    };


    useEffect(() => {
        if (lyricsRef.current) {
            setLyricsHeight(lyricsRef.current.clientHeight / lyrics.length);
        }
    }, [lyricsRef, lyrics]);

    useEffect(() => {
        if (!isLyricsVisible) {
            setCurrentLyricIndex(0);
            setCurrentWordIndex(0);
            setCurrentColor('black');
            setColorMap([]);
        }
    }, [isLyricsVisible]);

    const findAndUnderlineKeywords = (lyric) => {
        const newUnderlinedWords = [];

        lyric.Keywords.forEach((keyword) => {
            const normalizedText = lyric.Text;
            const normalizedKeyword = keyword;
            let startIndex = normalizedText.indexOf(normalizedKeyword);

            while (startIndex !== -1) {
                const endIndex = startIndex + normalizedKeyword.length - 1;
                newUnderlinedWords.push({keyword: keyword, start: startIndex, end: endIndex});
                startIndex = normalizedText.indexOf(normalizedKeyword, endIndex + 1);
            }
        });
        return newUnderlinedWords;
    };

    // const delay = (time) => {
    //     return new Promise(resolve => setTimeout(resolve, time));
    // }
    const wait = (ms) => {
        const start = Date.now();
        let now = start;
        while (now - start < ms) {
            now = Date.now();
        }
    };
    const highLightWords = () => {
        {
            const currentLyric = lyrics[currentLyricIndex];
            // const words = currentLyric.Text.split(/\s+/);
            const words = currentLyric.Text.split(' ');
            const currentWord = words[currentWordIndex];
            const lastCharacter = currentWord[currentWord.length - 1];
            console.log('start..........');
            if (currentWordIndex === words.length - 1) {
                setCurrentLyricIndex(currentLyricIndex === lyrics.length - 1 ? 0 : currentLyricIndex + 1);
                setCurrentWordIndex(0);
                scrollLyrics();
                console.log('delay..........');
                wait(2000);
            } else {
                setCurrentWordIndex(currentWordIndex + 1);
                if (lastCharacter === ':' || lastCharacter === ';' || lastCharacter === '.') {
                    scrollLyrics();
                }

            }

            const color = currentLyricId === 'TVTC' ? 'blue' : currentLyricId === 'KH' ? 'red' : 'black';
            setColor(currentLyricIndex, currentWordIndex, color);
            setCurrentColor(color);
        }
    }
    useEffect(() => {
        const currentLyric = lyrics[currentLyricIndex];
        if (currentLyric) {
            setCurrentLyricId(currentLyric.Id);
        }
    }, [currentLyricIndex]);

    useEffect(() => {
        const interval = setInterval(() => highLightWords(), mode);

        return () => clearInterval(interval);
    }, [currentWordIndex, currentLyricIndex, mode]);

    return (<div
        style={{
            maxHeight: '300px', overflow: 'auto', scrollBehavior: 'smooth', whiteSpace: 'pre-wrap',
        }}
        ref={lyricsRef}
    >
        {lyrics.map((lyric, lyricIndex) => {
            const words = lyric.Text.split(' ');
            // const words = lyric.Text.replace(/\n\s*-\s*/g, "\n- ").split(' ');

            let currentPosition = 0;
            return (// eslint-disable-next-line react/no-array-index-key
                <div key={lyricIndex} className="lyric-item">
            <span
                style={{
                    color: lyric.Id === 'TVTC' ? 'blue' : lyric.Id === 'KH' ? 'red' : 'black',
                }}
            >
              {lyric.Id?.replaceAll('TVTC', 'ĐLBH')}:
            </span>
                    {' '}
                    {words.map((word, wordIndex) => {
                        const isCurrentLyric = currentLyricIndex === lyricIndex;
                        const isCurrentWord = isCurrentLyric && currentWordIndex === wordIndex;
                        const color = (colorMap[lyricIndex] && colorMap[lyricIndex][wordIndex]) || '';
                        const isUnderlined = lyric.Keywords && lyric.Keywords.length !== 0 && findAndUnderlineKeywords(lyric).some((underlineInfo) => {
                            const isInRange = currentPosition >= underlineInfo.start && currentPosition <= underlineInfo.end;
                            const isEndOfKeyword = currentPosition === underlineInfo.end + 1; // Check if it's the space after the keyword
                            return isInRange || isEndOfKeyword;
                        });

                        currentPosition += word.length + 1; // Add 1 for the space between words

                        if (word.endsWith('?')) {
                            const wordsAndPunctuation = word.split(/(\?)/).filter(Boolean);
                            return (<>
                                {wordsAndPunctuation.map((part, index) => (<span key={index}>
                                                {part !== '?' ? (<span
                                                    key={index}
                                                    style={{
                                                        color: isCurrentWord ? currentColor : color,
                                                        textDecoration: 'underline',
                                                    }}
                                                >
                                                        {`${part.trim()}`}
                                            </span>) : (<span
                                                    key={index}
                                                    style={{
                                                        color: isCurrentWord ? currentColor : color,
                                                    }}
                                                >
                                                {`${part.trim()}`}
                                            </span>)}
                                        </span>))}
                            </>);
                        }

                        return (<>
                                <span
                                    key={wordIndex}
                                    style={{
                                        color: isCurrentWord ? currentColor : color,
                                        textDecoration: isUnderlined && word.trim() !== '' ? 'underline' : 'none',
                                    }}
                                >
        {`${word} `}
    </span>
                            {/*<span > </span>*/}
                        </>);
                    })}
                </div>);
        })}
    </div>);
}
