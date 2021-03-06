DROP TABLE CHAT;
DROP SEQUENCE CHAT_SEQ;

CREATE SEQUENCE CHAT_SEQ;

/*CREATE TABLE "CHAT" (
	"CHATNO" NUMBER		PRIMARY KEY,
	"USERNO" NUMBER		NOT NULL,
	"FILENO" NUMBER		NULL,
	"CHATTYPE" VARCHAR2(100)	NULL,
	"CHATCONTENT"	VARCHAR2(1000)	NULL,
	"VIDEOURL"	VARCHAR2(500)	NULL,
	"CHATCOLOR"	VARCHAR2(50)	NULL
);*/

CREATE TABLE "CHAT" (
	"CHATNO"	NUMBER		NOT NULL,
	"USERNO"	NUMBER		NOT NULL,
	"PAGENO"	NUMBER		NOT NULL,
	"CHATTYPE" VARCHAR2(100)	NULL,
	"CHATCONTENT"	VARCHAR2(4000)		NULL,
	"VIDEOURL"	VARCHAR2(2000)		NULL,
	"FILENO"	NUMBER		NULL,
	"CHATCOLOR"	VARCHAR(50)		NULL,
	"CHATTIME"	DATE		NULL,
	"CANVASNO"	NUMBER		NULL,
	"MAPNO"	NUMBER		NULL
);

SELECT * FROM CHAT ORDER BY CHATNO;

INSERT INTO CHAT VALUES(CHAT_SEQ.NEXTVAL, '1', '', 'msg','HIHIHIHIHI','https://www.youtube.com/embed/o_cdhpfEmLM');