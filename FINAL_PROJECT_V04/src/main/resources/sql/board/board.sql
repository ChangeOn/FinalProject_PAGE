-- ���̺� �� ������ �ʱ�ȭ
DROP SEQUENCE BOARDSEQ;
DROP SEQUENCE ANSWERSEQ;
DROP SEQUENCE FILESEQ;
DROP TABLE BOARD;
DROP TABLE ANSBOARD;
DROP TABLE FILES;

-- ���̺� �� ������ ����
CREATE SEQUENCE BOARDSEQ;
CREATE SEQUENCE ANSWERSEQ;
CREATE SEQUENCE FILESEQ;

CREATE TABLE BOARD(
	BOARDSEQ NUMBER PRIMARY KEY,
	ID VARCHAR2(200) NOT NULL, 
	BOARDNO NUMBER NOT NULL,
	TITLE VARCHAR2(1000) NOT NULL,
	CONTENT VARCHAR2(4000) NOT NULL,
	REGDATE DATE NOT NULL,
	FILENO NUMBER,
	VIEWCNT NUMBER,
	ANSCNT NUMBER
);

CREATE TABLE ANSBOARD(
	BOARDSEQ NUMBER NOT NULL,
	ANSNO NUMBER NOT NULL,
	ID VARCHAR2(200) NOT NULL,
	ANSCONTENT VARCHAR2(2000) NOT NULL,
	ANSREGDATE DATE NOT NULL
);

CREATE TABLE FILES(
	FILENO NUMBER PRIMARY KEY,
	FILESTREAM VARCHAR2(3000),
	FILETITLE VARCHAR2(1000)
)

INSERT INTO BOARD VALUES(BOARDSEQ.NEXTVAL,'KH',0,'�׽�Ʈ','�׽�Ʈ���Դϴ�.',SYSDATE,0,0);
INSERT INTO ANSBOARD VALUES(1,ANSWERSEQ.NEXTVAL,'KH','�׽�Ʈ',SYSDATE);
		UPDATE BOARD SET ANSCNT = ANSCNT+1 WHERE BOARDSEQ = 1 ;

INSERT INTO ANSBOARD VALUES(1,ANSWERSEQ.NEXTVAL,'��漱','��漱 �׽�Ʈ',SYSDATE);
		UPDATE BOARD SET ANSCNT = ANSCNT+1 WHERE BOARDSEQ = 1 ;

 		
SELECT * FROM ANSBOARD;
SELECT * FROM FILES;
