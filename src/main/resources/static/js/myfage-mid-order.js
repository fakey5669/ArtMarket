const myfageMid = document.querySelector('.myfage-mid');
const myfageRight = document.querySelector('.myfage-right');

function myfageOrderMidAddTag() {
    if (myfageMid.childNodes) {
        for (myfageMidTag of myfageMid.childNodes) {
            myfageMidTag.remove();
        }
    }

    if (myfageRight.childNodes) {
        for (myfageRightTag of myfageRight.childNodes) {
            myfageRightTag.remove();
        }
    }
    myfageMid.innerHTML = `<!DOCTYPE html>
    <html lang="ko">
    <head>
        <meta charset="UTF-8" />
        <meta name="viewport" content="width=device-width, initial-scale=1.0" />
        <title>myfage</title>
        <script async src="./js/nav.js"></script>
        <script async src="./js/footer.js"></script>
        <script async src="./js/all.js"></script>
        <script async src="./js/myfage-nav.js"></script>
        <script async src="./js/myfage-mid-order.js"></script>
        <script async src="./js/myfage-mid-info.js"></script>
        <script async src="./js/myfage-mid-chat.js"></script>
        <script async src="./js/myfage-mid-article.js"></script>
        <script async src="./js/myfage-right-info.js"></script>
        <script async src="./js/myfage-right-chat.js"></script>
        <script async src="./js/myfage-right-article.js"></script>
        <script defer src="./js/myfage-delete-article.js"></script>
        <script defer src="./js/myfage-modify-article.js"></script>
        <script async src="./js/myfage-nav-click.js"></script>
        <script async src="./js/chat.js"></script>
        <link rel="stylesheet" href="./css/default/nav.css" />
        <link rel="stylesheet" href="./css/default/footer.css" />
        <link rel="stylesheet" href="./css/default/all.css" />
        <link rel="stylesheet" href="./css/myfage/myfage-nav.css" />
        <link rel="stylesheet" href="./css/myfage/myfage-mid-chat.css" />
        <link rel="stylesheet" href="./css/myfage/myfage-right-chat.css" />
        <link rel="stylesheet" href="./css/myfage/myfage-mid-article.css" />
        <link rel="stylesheet" href="./css/myfage/myfage-right-article.css" />
        <link rel="stylesheet" href="./css/myfage/myfage-mid-info.css" />
        <link rel="stylesheet" href="./css/myfage/myfage-right-info.css" />
        <link rel="stylesheet" href="./css/myfage/myfage-mid-order.css" />
    </head>
    <body>
        <nav></nav>
        <container>
            <div class="myfage-nav"></div>
            <div class="myfage-mid">
                <!-- -->
                <div class="myfage-mid-order-content">
                    <!-- -->
                    <div class="myfage-mid-order-content-nav">
                        <div class="myfage-mid-order-content-type-req">의뢰관리</div>
                        <div class="myfage-mid-order-content-type-art">작업관리</div>
                    </div>
                    <!-- -->
                    <div class="myfage-mid-order-content-status">
                        <div class="status-1">
                            <div class="status-1-label">의뢰 신청</div>
                            <div class="status-1-value">0건</div>
                        </div>
                        <div class="status-2">
                            <div class="status-2-label">의뢰 진행중</div>
                            <div class="status-2-value">0건</div>
                        </div>
                        <div class="status-3">
                            <div class="status-3-label">처리 완료</div>
                            <div class="status-3-value">0건</div>
                        </div>
                        <div class="status-4">
                            <div class="status-4-label">작품 수령</div>
                            <div class="status-4-value">0건</div>
                        </div>
                        <div class="status-5">
                            <div class="status-5-label">의뢰 취소</div>
                            <div class="status-5-value">0건</div>
                        </div>
                    </div>
                    <!-- -->
                    <div class="myfage-mid-order-content-filter">
                        <select class="myfage-mid-order-content-filter-current">
                            <option>전체 상태</option>
                            <option id="status-1"></option>
                            <option id="status-2"></option>
                            <option id="status-3"></option>
                            <option id="status-4"></option>
                            <option id="status-5"></option>
                        </select>
                        <input type="date" class="myfage-mid-order-content-filter-start" />
                        <input type="date" class="myfage-mid-order-content-filter-end" />
                    </div>
                    <!-- -->
                    <div class="myfage-mid-order-content-list-box">
                        <!-- -->
                        <div class="myfage-mid-order-content-list-title">
                            <div class="myfage-mid-order-content-list-title-order-num">
                                의뢰번호
                            </div>
                            <div class="myfage-mid-order-content-list-title-order-nickname">
                                작가명
                            </div>
                            <div class="myfage-mid-order-content-list-title-order-cost">
                                결제금액
                            </div>
                            <div class="myfage-mid-order-content-list-title-order-date">
                                주문날짜
                            </div>
                            <div class="myfage-mid-order-content-list-title-order-deadline">
                                마감기한
                            </div>
                            <div class="myfage-mid-order-content-list-title-order-status">
                                주문상태
                            </div>
                        </div>
                        <!-- -->
                        <div class="myfage-mid-order-content-list-value">
                            <div class="myfage-mid-order-content-list-value-order-num">1</div>
                            <div class="myfage-mid-order-content-list-value-order-nickname">
                                asdras
                            </div>
                            <div class="myfage-mid-order-content-list-value-order-cost">
                                200,000
                            </div>
                            <div class="myfage-mid-order-content-list-value-order-date">
                                2023-09-26
                            </div>
                            <div class="myfage-mid-order-content-list-value-order-deadline">
                                2023-09-26
                            </div>
                            <div class="myfage-mid-order-content-list-value-order-status">
                                의뢰 신청
                            </div>
                        </div>
                        <!-- -->
                    </div>
                    <!-- -->
                </div>
                <!-- -->
            </div>
            <div class="myfage-right"></div>
        </container>
        <footer></footer>
        <script src="https://cdn.jsdelivr.net/npm/sockjs-client@1/dist/sockjs.min.js"></script>
        <script src="https://cdnjs.cloudflare.com/ajax/libs/stomp.js/2.3.3/stomp.min.js"></script>
    </body>
    </html>`;
}

// myfageOrderMidAddTag(); // 페이지 로딩 후 바로 실행할 경우