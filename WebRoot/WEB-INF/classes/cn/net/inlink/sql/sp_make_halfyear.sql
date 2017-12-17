CREATE OR REPLACE PROCEDURE SP_MAKE_HALFYEAR
/*
�ô洢���������ɰ����̵����農̬����ݣ��˹ҡ�FB�����ɣ�����ݹ������ֶν����ж�ȡ�ñ��еĿ����ֶ�
��̬�����ȡ��BSITE ����ȡ�����Ŀ��Ҽ�¼���뵽��̬����

*/

AS

C_SQL VARCHAR2(1024);--ƴ��sql

N_HAVE NUMBER ;-- �������


--�����α�
 C_RULE sys_refcursor;


--���������ͱ���
V_RULE PRC_INVENTORY_RULE%ROWTYPE;


BEGIN
  --���Ƚ�������еĿռ�¼ɾ��ɾ�����ΪLOCATIONCODE����Ϊnull
  C_SQL:= 'DELETE FROM PRC_INVENTORY_RULE WHERE LOCATIONCODE IS NULL';
  EXECUTE IMMEDIATE C_SQL;
--�˹�
  C_SQL:='TRUNCATE TABLE WIP_STATISTICS_L_TMP'; 
  EXECUTE IMMEDIATE C_SQL;
  
  C_SQL:='TRUNCATE TABLE WIP_STATISTICS_L'; 
  EXECUTE IMMEDIATE C_SQL;
  
  C_SQL:='TRUNCATE TABLE FB_STATISTICS_L_TMP';
  EXECUTE IMMEDIATE C_SQL;
  
  C_SQL:='TRUNCATE TABLE FB_STATISTICS_L';
  EXECUTE IMMEDIATE C_SQL;


  --ȡ��ǰ�˹ҵļ�¼����
  SELECT COUNT(*) INTO N_HAVE FROM BSITE_WIP;
  

  
   
  --�ж�
  IF N_HAVE>0 THEN
    
  
   --���˹Ҿ�̬���в������ 
   INSERT INTO WIP_STATISTICS_L_TMP(LOTNAME,PRODUCT,�ֻ�,PREVLOT,LOCATIONCODE,OPERATION,QTY,PKG,FAMILYL,FAMILYMCODE,SHIPMENTORDER,COSTCODE)
    SELECT * FROM VIEW_HALFYEAR;
    /*SELECT B1.LOTNAME,B1.PRODUCT,SUBSTR(B1.PRODUCT,LENGTH(PRODUCT)-3),B1.PREVLOT,B1.LOCATIONCODE,B1.OPERATION,
        B1.QTY,SUBSTR(B1.PROCESS,1,6),B1.FAMILYL,B2.FAMILYLCODE ,B3.SHIPMENTORDER FROM BSITE_WIP B1 
         LEFT JOIN BSITE_PRODUCT B2 ON  B1.PRODUCT = B2.PRODUCTNAME
         LEFT JOIN BSITE_WIP_REQUESTORDERSTATUS B3 ON  B1.LOTNAME = B3.LOTNAME 
         WHERE B1.LOCATIONCODE NOT LIKE 'ZC%' ;*/
    
   INSERT INTO WIP_STATISTICS_L(LOTNAME,PRODUCT,�ֻ�,PREVLOT,LOCATIONCODE,OPERATION,QTY,PKG,FAMILYL,FAMILYMCODE,SHIPMENTORDER,COSTCODE) SELECT * FROM WIP_STATISTICS_L_TMP;
   
   --��FB��̬���в������
   INSERT INTO FB_STATISTICS_L_TMP(LOTNAME,PRODUCT,LOCATIONCODE,QTY,PKG,FAMILYL,FAMILYMCODE,COSTCODE)
     SELECT B.LOTNAME,B.PRODUCT,B.LOCATIONCODE,B.QTY,SUBSTR(B.PROCESS,1,6),B.FAMILYL,
     (SELECT B1.FAMILYMCODE FROM BSITE_PRODUCT B1 WHERE B1.PRODUCTNAME=B.PRODUCT) AS FAMILYMCODE,B.COSTCODE
      FROM BSITE_FB B 
        WHERE B.LOCATIONCODE NOT LIKE 'ZC%';   
     
   INSERT INTO FB_STATISTICS_L(LOTNAME,PRODUCT,LOCATIONCODE,QTY,PKG,FAMILYL,FAMILYMCODE,COSTCODE) SELECT * FROM FB_STATISTICS_L_TMP;
   
   
   
  --���˹Ҿ�̬���е�SHIPMENTORDERΪnull�ĸ�ֵΪ0
  UPDATE WIP_STATISTICS_L SET SHIPMENTORDER = 0 WHERE SHIPMENTORDER IS NULL;
  --将将仕挂静态表中非T98工程的SHIPMENTORDER赋值为0
  UPDATE WIP_STATISTICS_L SET SHIPMENTORDER = 0 WHERE OPERATION <>'T98';
  --��������еĴ��ֶ�Ϊnull�ĸ�ֵΪ0
  UPDATE PRC_INVENTORY_RULE SET WAITDECIDE = 0 WHERE WAITDECIDE IS NULL;
  COMMIT;
  --ѭ������ȡ��������еļ�¼ 
  
  /*
    ������� pkg �� ���� ������ ���Ϊ������Ϊɸѡ����
  */
    OPEN C_RULE FOR SELECT * FROM PRC_INVENTORY_RULE;
   LOOP
      FETCH C_RULE INTO V_RULE;
      
      EXIT WHEN C_RULE%NOTFOUND;
      
      
       --�ж�pkg�����ܡ�����
       IF V_RULE.FAMILYMCODE IS NULL AND V_RULE.PRODUCT IS  NULL AND V_RULE.PKG IS  NULL THEN
         
      
         UPDATE WIP_STATISTICS_L W SET W.DEPARTMENT = V_RULE.DEPARTMENT WHERE W.LOCATIONCODE=V_RULE.LOCATIONCODE AND
                                                                     W.OPERATION=V_RULE.OPERATIONNAME AND
                                                                     W.SHIPMENTORDER=V_RULE.WAITDECIDE;
         
            
         --FB                                                  
         UPDATE FB_STATISTICS_L F SET F.DEPARTMENT = V_RULE.DEPARTMENT WHERE F.LOCATIONCODE=V_RULE.LOCATIONCODE AND
                                                                       V_RULE.OPERATIONNAME = 'A26';
                                                                                                                              
       END IF;
      
      --PKG��������ܶ���Ϊnull
      IF V_RULE.PKG IS NOT NULL AND V_RULE.FAMILYMCODE IS NOT NULL AND V_RULE.PRODUCT IS NOT NULL THEN 
       --�˹� 
       UPDATE WIP_STATISTICS_L W SET W.DEPARTMENT = V_RULE.DEPARTMENT WHERE W.LOCATIONCODE=V_RULE.LOCATIONCODE AND
                                                                     W.OPERATION=V_RULE.OPERATIONNAME   AND
                                                                     W.FAMILYMCODE LIKE V_RULE.FAMILYMCODE AND 
                                                                     W.PRODUCT LIKE V_RULE.PRODUCT AND
                                                                     W.PKG = V_RULE.PKG AND 
                                                                     W.SHIPMENTORDER=V_RULE.WAITDECIDE;
                                                                     
        --FB                                                  
         UPDATE FB_STATISTICS_L F SET F.DEPARTMENT = V_RULE.DEPARTMENT WHERE F.LOCATIONCODE=V_RULE.LOCATIONCODE AND
                                                                       V_RULE.OPERATIONNAME = 'A26' AND 
                                                                       F.PRODUCT LIKE V_RULE.PRODUCT AND
                                                                       F.PKG = V_RULE.PKG AND 
                                                                       F.FAMILYMCODE LIKE V_RULE.FAMILYMCODE;                      
       END IF;
       
      --�ж�pkg
       IF V_RULE.PKG IS NULL AND V_RULE.FAMILYMCODE IS NOT NULL AND V_RULE.PRODUCT IS NOT NULL THEN
         
   
         --�˹�
         UPDATE WIP_STATISTICS_L W SET W.DEPARTMENT = V_RULE.DEPARTMENT WHERE W.LOCATIONCODE=V_RULE.LOCATIONCODE AND
                                                                     W.OPERATION=V_RULE.OPERATIONNAME   AND
                                                                     W.FAMILYMCODE LIKE V_RULE.FAMILYMCODE AND 
                                                                     W.PRODUCT LIKE V_RULE.PRODUCT AND
                                                                     W.SHIPMENTORDER=V_RULE.WAITDECIDE;
          
                                                                          
                                                                 
         --FB                                                  
         UPDATE FB_STATISTICS_L F SET F.DEPARTMENT = V_RULE.DEPARTMENT WHERE F.LOCATIONCODE=V_RULE.LOCATIONCODE AND
                                                                       V_RULE.OPERATIONNAME = 'A26' AND 
                                                                       F.PRODUCT LIKE V_RULE.PRODUCT AND 
                                                                       F.FAMILYMCODE LIKE V_RULE.FAMILYMCODE 
                                                                       ;
       END IF;
       
       --�жϻ���
       IF V_RULE.FAMILYMCODE IS NULL AND V_RULE.PKG IS NOT NULL AND V_RULE.PRODUCT IS NOT NULL THEN
         --�˹�
         UPDATE WIP_STATISTICS_L W SET W.DEPARTMENT = V_RULE.DEPARTMENT WHERE W.LOCATIONCODE=V_RULE.LOCATIONCODE AND
                                                                     W.OPERATION=V_RULE.OPERATIONNAME   AND
                                                                     W.PKG = V_RULE.PKG AND 
                                                                     W.PRODUCT LIKE V_RULE.PRODUCT AND
                                                                     W.SHIPMENTORDER=V_RULE.WAITDECIDE;
        
        
         
         --FB                                                  
         UPDATE FB_STATISTICS_L F SET F.DEPARTMENT = V_RULE.DEPARTMENT WHERE F.LOCATIONCODE=V_RULE.LOCATIONCODE AND
                                                                       V_RULE.OPERATIONNAME = 'A26' AND 
                                                                       F.PKG = V_RULE.PKG AND 
                                                                       F.PRODUCT LIKE V_RULE.PRODUCT;
       END IF;
       
       --�ж�����
       IF V_RULE.PRODUCT IS NULL AND V_RULE.PKG IS NOT NULL AND V_RULE.FAMILYMCODE IS NOT NULL THEN
         --�˹�
         
         
         UPDATE WIP_STATISTICS_L W SET W.DEPARTMENT = V_RULE.DEPARTMENT WHERE W.LOCATIONCODE=V_RULE.LOCATIONCODE AND
                                                                     W.OPERATION=V_RULE.OPERATIONNAME   AND
                                                                     W.PKG = V_RULE.PKG AND
                                                                     W.FAMILYMCODE LIKE V_RULE.FAMILYMCODE AND
                                                                     W.SHIPMENTORDER=V_RULE.WAITDECIDE;
      
        
        --FB                                                  
         UPDATE FB_STATISTICS_L F SET F.DEPARTMENT = V_RULE.DEPARTMENT WHERE F.LOCATIONCODE=V_RULE.LOCATIONCODE AND
                                                                       V_RULE.OPERATIONNAME = 'A26' AND 
                                                                       F.PKG = V_RULE.PKG AND 
                                                                       F.FAMILYMCODE LIKE V_RULE.FAMILYMCODE; 
                                                                      
       END IF;
       
       --�ж�pkg������
       IF V_RULE.PKG IS NULL AND V_RULE.FAMILYMCODE IS  NULL AND V_RULE.PRODUCT IS NOT NULL THEN
         --�˹�
        
         UPDATE WIP_STATISTICS_L W SET W.DEPARTMENT = V_RULE.DEPARTMENT WHERE W.LOCATIONCODE=V_RULE.LOCATIONCODE AND
                                                                     W.OPERATION=V_RULE.OPERATIONNAME   AND
                                                                     W.PRODUCT LIKE V_RULE.PRODUCT AND
                                                                     W.SHIPMENTORDER=V_RULE.WAITDECIDE;
                
          
          --FB                                                  
         UPDATE FB_STATISTICS_L F SET F.DEPARTMENT = V_RULE.DEPARTMENT WHERE F.LOCATIONCODE=V_RULE.LOCATIONCODE AND
                                                                       V_RULE.OPERATIONNAME = 'A26' AND 
                                                                       F.PRODUCT LIKE V_RULE.PRODUCT ;                                                           
       END IF;
       
       --�ж�pkg������
       IF V_RULE.PKG IS NULL AND V_RULE.PRODUCT IS  NULL AND V_RULE.FAMILYMCODE IS NOT NULL THEN
         --�˹�
                         
        UPDATE WIP_STATISTICS_L W SET W.DEPARTMENT = V_RULE.DEPARTMENT WHERE W.LOCATIONCODE=V_RULE.LOCATIONCODE AND
                                                                     W.OPERATION=V_RULE.OPERATIONNAME   AND
                                                                     W.FAMILYMCODE LIKE V_RULE.FAMILYMCODE AND
                                                                     W.SHIPMENTORDER=V_RULE.WAITDECIDE;
                                                                     
         --FB                                                  
         UPDATE FB_STATISTICS_L F SET F.DEPARTMENT = V_RULE.DEPARTMENT WHERE F.LOCATIONCODE=V_RULE.LOCATIONCODE AND
                                                                       V_RULE.OPERATIONNAME = 'A26'AND 
                                                                       F.FAMILYMCODE LIKE V_RULE.FAMILYMCODE;
       END IF;
      
      --�жϻ��ܡ�����
       IF V_RULE.FAMILYMCODE IS NULL AND V_RULE.PRODUCT IS  NULL AND V_RULE.PKG IS NOT NULL THEN
              
         UPDATE WIP_STATISTICS_L W SET W.DEPARTMENT = V_RULE.DEPARTMENT WHERE W.LOCATIONCODE=V_RULE.LOCATIONCODE AND
                                                                     W.OPERATION=V_RULE.OPERATIONNAME   AND
                                                                     W.PKG = V_RULE.PKG AND
                                                                     W.SHIPMENTORDER=V_RULE.WAITDECIDE;
  
         
         
        --FB                                                  
         UPDATE FB_STATISTICS_L F SET F.DEPARTMENT = V_RULE.DEPARTMENT WHERE F.LOCATIONCODE=V_RULE.LOCATIONCODE AND
                                                                       V_RULE.OPERATIONNAME = 'A26' AND
                                                                       F.PKG = V_RULE.PKG;
                                                                      
       END IF;
       
      
       
   END LOOP;
   
   CLOSE C_RULE;
   
    
    --����������Ϊ�յ��ֶθ�ֵΪ�޿��ң��˹ң�
    UPDATE WIP_STATISTICS_L SET DEPARTMENT = '�޿���' WHERE DEPARTMENT IS NULL;
    
    --FB
    UPDATE FB_STATISTICS_L SET DEPARTMENT = '�޿���' WHERE DEPARTMENT IS NULL;
    
    --���˹ҡ�fb���п��ұ��
    
   UPDATE WIP_STATISTICS_L W SET FLAG = (SELECT DD.FLAG FROM PRC_DIST_DEPT DD WHERE DD.DEPARTMENT=W.DEPARTMENT);
      
   UPDATE FB_STATISTICS_L F SET FLAG = (SELECT DD.FLAG FROM PRC_DIST_DEPT DD WHERE DD.DEPARTMENT=F.DEPARTMENT);
   
   --ȡ���˹ұ��еĿ��ұ�ǲ����¼����
   C_SQL:='TRUNCATE TABLE PRC_WIP_DISTDEPART';
   EXECUTE IMMEDIATE C_SQL; 
   
   INSERT INTO PRC_WIP_DISTDEPART(FLAG,DEPARTMENT) SELECT DISTINCT FLAG,DEPARTMENT FROM WIP_STATISTICS_L ORDER BY FLAG ;
   INSERT INTO PRC_WIP_DISTDEPART(FLAG,DEPARTMENT) VALUES('DownloadXCH','���ɳ�');
   INSERT INTO PRC_WIP_DISTDEPART(FLAG,DEPARTMENT) VALUES('DownloadCom','��˾��');
   
   UPDATE PRC_WIP_DISTDEPART SET UPLOADADD = '�����ϴ�';
   
   --�Բ��ű��һ��sheet���л���qty�������̺�����ߣ�
   C_SQL:='TRUNCATE TABLE WIP_STATISTICS_FINAL';
   EXECUTE IMMEDIATE C_SQL;
   
   INSERT INTO WIP_STATISTICS_FINAL SELECT locationcode,operation,department,flag,SUM(qty) AS qty FROM wip_statistics_l GROUP BY locationcode,operation,department,flag;
 
   
   --��fb���ű��һsheet���л���qty
   C_SQL :='TRUNCATE TABLE FB_STATISTICS_FINAL';
   EXECUTE IMMEDIATE C_SQL;
   
   INSERT INTO FB_STATISTICS_FINAL SELECT locationcode,department,flag,SUM(qty) AS qty FROM fb_statistics_l GROUP BY locationcode,department,flag;
   
   --����
   INSERT INTO FB_STATISTICS_FINAL SELECT '�ϼ�' AS locationcode ,department,flag ,SUM(qty) AS qty FROM FB_STATISTICS_FINAL  GROUP BY department,flag;
   
   COMMIT;

  END IF;
  ------------------------------------------------------------------------------------------------------------
  --����(װ��ǰ)
  
  C_SQL := 'TRUNCATE TABLE XCH_STATISTICS_BEFORE_TMP';
  EXECUTE IMMEDIATE C_SQL;
  
  C_SQL :='TRUNCATE TABLE XCH_STATISTICS_BEFORE';
  EXECUTE IMMEDIATE C_SQL;
  
  --ȡ����ǰ����״̬���еļ�¼��
  SELECT COUNT(*) INTO N_HAVE FROM XCH_GZT1010;
  
  --�ж�
  IF N_HAVE>0 THEN
    INSERT INTO XCH_STATISTICS_BEFORE_TMP
      SELECT X.KEY_NO,X.WIP_MODEL_BODY,X.WIP_MODEL_REPO,X.QTY,X.CNTL_FLG FROM XCH_GZT1010 X WHERE X.CNTL_FLG='0';
   
  --��װ��ǰ��̬��������
    INSERT INTO XCH_STATISTICS_BEFORE SELECT * FROM XCH_STATISTICS_BEFORE_TMP;
    
  --ͳ��qty����
    INSERT INTO XCH_STATISTICS_BEFORE(LOTNAME,QTY) SELECT '�ϼ�' AS LOTNAME,SUM(QTY) AS QTY FROM XCH_STATISTICS_BEFORE;
 
 END IF;

 --���ɣ�װ���
  C_SQL :='TRUNCATE TABLE XCH_STATISTICS_AFTER_TMP';
  EXECUTE IMMEDIATE C_SQL;
  
  C_SQL :='TRUNCATE TABLE XCH_STATISTICS_AFTER';
  EXECUTE IMMEDIATE C_SQL;
  
  --ȡ����ǰ����״̬���еļ�¼��
  SELECT COUNT(*) INTO N_HAVE FROM XCH_GZT1170;
  
  --�ж�
  IF N_HAVE>0 THEN
    INSERT INTO XCH_STATISTICS_AFTER_TMP
      SELECT X.PKG_ID,X.BALE_QTY,X.BOX_QTY,X.D_ORDER_NO,X.D_ORDER_NO_BRANCH,X.CNTL_FLG FROM XCH_GZT1170 X WHERE X.CNTL_FLG='2';
   
  --��װ���̬��������
    INSERT INTO XCH_STATISTICS_AFTER SELECT * FROM XCH_STATISTICS_AFTER_TMP; 
    
  --Tͳ������������������
    INSERT INTO XCH_STATISTICS_AFTER(PKGID,BALEQTY,BOXQTY) SELECT '�ϼ�' AS PKGID,SUM(BALEQTY) AS BALEQTY,SUM(BOXQTY)AS BOXQTY FROM  XCH_STATISTICS_AFTER;
  COMMIT;
 END IF;
 
 
 -----------------------------------------------------------------------------------------------
 
 --���?˾��ͳ������ľ�̬��
 
 --��ձ��
 C_SQL:='TRUNCATE TABLE COMPANY_STATISTICS_TMP';
 EXECUTE IMMEDIATE C_SQL;
 
 C_SQL:='TRUNCATE TABLE COMPANY_STATISTICS';
 EXECUTE IMMEDIATE C_SQL;
 
 --�˹Ҹ�����ͳ��
 INSERT INTO COMPANY_STATISTICS_TMP SELECT 'FB'||DEPARTMENT AS DEPARTMENT,SUM(QTY) AS QTY FROM FB_STATISTICS_L GROUP BY DEPARTMENT;

 
 --fb������ͳ��
 INSERT INTO COMPANY_STATISTICS_TMP SELECT DEPARTMENT ,SUM(QTY)AS QTY FROM WIP_STATISTICS_L GROUP BY DEPARTMENT;

 --���ɳ�װ��ǰ
 INSERT INTO COMPANY_STATISTICS_TMP SELECT  '���ɳ�װ��ǰ' AS DEPARTMENT ,SUM(QTY) AS QTY FROM XCH_STATISTICS_BEFORE;

 --�ܼ�����
 INSERT INTO COMPANY_STATISTICS_TMP SELECT '�ܼ�' AS DEPARTMENT ,SUM(QTY) AS QTY FROM COMPANY_STATISTICS_TMP;
 
 --��̬���в������
 INSERT INTO COMPANY_STATISTICS SELECT * FROM COMPANY_STATISTICS_TMP;
 
 COMMIT;
 
 
END;
