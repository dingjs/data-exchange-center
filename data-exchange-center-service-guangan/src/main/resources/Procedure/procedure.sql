CREATE OR REPLACE Procedure SERVICE_GUANGAN(
                              adAjbs      In      Varchar2,
                              arAjxx      Out DCADM.DC_COMMON.t_RefCur,
                              arSksfw     Out DCADM.DC_COMMON.t_RefCur,
                              arCyrxx     Out DCADM.DC_COMMON.t_RefCur,
                              arZxcsxx    Out DCADM.DC_COMMON.t_RefCur,
                              arZxak      Out DCADM.DC_COMMON.t_RefCur,
                              arZxqk      Out DCADM.DC_COMMON.t_RefCur,
                              arJafs      Out DCADM.DC_COMMON.t_RefCur,
                              arJafs1     Out DCADM.DC_COMMON.t_RefCur,
                              anFlag      In Out  Number,
                              avErrmsg    In Out  Varchar2)
    Authid Current_user
    As
    vAjlxNum    Varchar2(19);
    vAjlxEn     Varchar2(19);
    vFb         Number;
    vAjjzjd     Number;
    vSql        Varchar2(4000);
    vSpt        Varchar2(19);
    vSqlerrm    Varchar2(4000);
  Begin
    anFlag := -1;
    avErrmsg  := '参数不能为空';
    --查询出数字案件类型
    SELECT AJLX INTO vAjlxNum FROM SGY.BUF_EAJ WHERE AJBS = ''||adAjbs||'';
    --查询出数字案件类型对应的表名前缀
    SELECT C_EPREFIX INTO vAjlxEn FROM DCADM.DC_AJLXLIST WHERE C_AJLX = ''||vAjlxNum||'';
    --案件信息 如果承办审判庭标识为空 则返回空
    SELECT CBSPTBS INTO vSpt FROM SGY.BUF_EAJ WHERE AJBS = ''||adAjbs||'';
    
    SELECT CAST(AJJZJD AS Number) INTO vAjjzjd FROM SGY.BUF_EAJ WHERE AJBS = ''||adAjbs||'';
    
    SELECT CAST(C_FB AS Number) INTO vFb FROM  DCADM.DC_AJLXLIST WHERE C_AJLX = ''||vAjlxNum||'';
    
    --法标内案件进展阶段>=7时，查询_JAQK
    --法标外案件进展阶段>=7时，查询_JAXX
    
    --法标内
    IF vFb = 0 THEN
      --审判庭不为空
      IF vSpt IS NOT NULL THEN
        IF vAjjzjd >= 7 THEN
          vSql := 'SELECT T1.AJBS, AJLX, T2.C_NAME AS AJLXMC, DEPTCODE, AH,
                     AJJZJD, 
                     TO_CHAR(T1.LARQ,''yyyymmdd'') LARQ, 
                     TO_CHAR(T1.JARQ,''yyyymmdd'') JARQ,
                     LAAY, CBSPT, CBSPTBS, CBR, 
                     T3.C_AJLB AJLB,
                     T3.C_NAME AJLBNAME, 
                     T2.C_FB FB,
                     YX, JGJB, FYBZ, DM,
                     T5.JAFS, T5.JAFSX,
                     TO_CHAR(T1.LASTUPDATE,''yyyymmdd'') LASTUPDATE
                FROM SGY.BUF_EAJ T1, 
                     DCADM.DC_AJLXLIST T2,
                     DCADM.DC_AJLBLIST T3,
                     SGY.SPZT_ZZJG T4,
                     SGY.'||vAjlxEn||'_JAQK T5
               WHERE T1.AJLX = T2.C_AJLX
                 AND DEPTCODE LIKE ''5126%''
                 AND T2.C_AJLB = T3.C_AJLB
                 AND T1.CBSPTBS = T4.ZZJGBZ
                 AND T1.AJBS = T5.AJBS
                 AND T1.AJBS = '||adAjbs||'';
          Open arAjxx For vSql;
        ELSE 
          vSql := 'SELECT T1.AJBS, AJLX, T2.C_NAME AS AJLXMC, DEPTCODE, AH,
                     AJJZJD, 
                     TO_CHAR(T1.LARQ,''yyyymmdd'') LARQ, 
                     TO_CHAR(T1.JARQ,''yyyymmdd'') JARQ,
                     LAAY, CBSPT, CBSPTBS, CBR, 
                     T3.C_AJLB AJLB,
                     T3.C_NAME AJLBNAME, 
                     T2.C_FB FB,
                     YX, JGJB, FYBZ, DM,
                     ''''JAFS, 
                     ''''JAFSX,
                     TO_CHAR(T1.LASTUPDATE,''yyyymmdd'') LASTUPDATE
                FROM SGY.BUF_EAJ T1, 
                     DCADM.DC_AJLXLIST T2,
                     DCADM.DC_AJLBLIST T3,
                     SGY.SPZT_ZZJG T4
               WHERE T1.AJLX = T2.C_AJLX
                 AND DEPTCODE LIKE ''5126%''
                 AND T2.C_AJLB = T3.C_AJLB
                 AND T1.CBSPTBS = T4.ZZJGBZ
                 AND T1.AJBS = '||adAjbs||'';
          Open arAjxx For vSql;
        END IF;
      ELSE
        IF vAjjzjd >= 7 THEN
          vSql := 'SELECT T1.AJBS, AJLX, T2.C_NAME AS AJLXMC, DEPTCODE, AH,
                     AJJZJD, 
                     TO_CHAR(T1.LARQ,''yyyymmdd'') LARQ, 
                     TO_CHAR(T1.JARQ,''yyyymmdd'') JARQ,
                     LAAY, CBSPT, CBSPTBS, CBR, 
                     T3.C_AJLB AJLB,
                     T3.C_NAME AJLBNAME, 
                     T2.C_FB FB,
                     '''' YX, '''' JGJB, '''' FYBZ, '''' DM,
                     T5.JAFS, T5.JAFSX,
                     TO_CHAR(T1.LASTUPDATE,''yyyymmdd'') LASTUPDATE
                FROM SGY.BUF_EAJ T1, 
                     DCADM.DC_AJLXLIST T2,
                     DCADM.DC_AJLBLIST T3,
                      SGY.'||vAjlxEn||'_JAQK T5
               WHERE T1.AJLX = T2.C_AJLX
                 AND DEPTCODE LIKE ''5126%''
                 AND T2.C_AJLB = T3.C_AJLB
                 AND T1.AJBS = T5.AJBS
                 AND T1.AJBS = '||adAjbs||'';
          Open arAjxx For vSql;
        ELSE
          vSql := 'SELECT T1.AJBS, AJLX, T2.C_NAME AS AJLXMC, DEPTCODE, AH,
                     AJJZJD, 
                     TO_CHAR(T1.LARQ,''yyyymmdd'') LARQ, 
                     TO_CHAR(T1.JARQ,''yyyymmdd'') JARQ,
                     LAAY, CBSPT, CBSPTBS, CBR, 
                     T3.C_AJLB AJLB,
                     T3.C_NAME AJLBNAME, 
                     T2.C_FB FB,
                     '''' YX, '''' JGJB, '''' FYBZ, '''' DM,
                     ''''JAFS, ''''JAFSX,
                     TO_CHAR(T1.LASTUPDATE,''yyyymmdd'') LASTUPDATE
                FROM SGY.BUF_EAJ T1, 
                     DCADM.DC_AJLXLIST T2,
                     DCADM.DC_AJLBLIST T3
               WHERE T1.AJLX = T2.C_AJLX
                 AND DEPTCODE LIKE ''5126%''
                 AND T2.C_AJLB = T3.C_AJLB
                 AND T1.AJBS = '||adAjbs||'';
          Open arAjxx For vSql;
        END IF;
      END IF;
    ELSIF vFb = 1 THEN
      --审判庭不为空
      IF vSpt IS NOT NULL THEN
        IF vAjjzjd >= 7 THEN
          vSql := 'SELECT T1.AJBS, AJLX, T2.C_NAME AS AJLXMC, DEPTCODE, AH,
                     AJJZJD, 
                     TO_CHAR(T1.LARQ,''yyyymmdd'') LARQ, 
                     TO_CHAR(T1.JARQ,''yyyymmdd'') JARQ,
                     LAAY, CBSPT, CBSPTBS, CBR, 
                     T3.C_AJLB AJLB,
                     T3.C_NAME AJLBNAME, 
                     T2.C_FB FB,
                     YX, JGJB, FYBZ, DM,
                     T5.JAFS, T5.JAFSX,
                     TO_CHAR(T1.LASTUPDATE,''yyyymmdd'') LASTUPDATE
                FROM SGY.BUF_EAJ T1, 
                     DCADM.DC_AJLXLIST T2,
                     DCADM.DC_AJLBLIST T3,
                     SGY.SPZT_ZZJG T4,
                     SGY.'||vAjlxEn||'_JAXX T5
               WHERE T1.AJLX = T2.C_AJLX
                 AND DEPTCODE LIKE ''5126%''
                 AND T2.C_AJLB = T3.C_AJLB
                 AND T1.CBSPTBS = T4.ZZJGBZ
                 AND T1.AJBS = T5.AJBS
                 AND T1.AJBS = '||adAjbs||'';
          Open arAjxx For vSql;
        ELSE 
          vSql := 'SELECT T1.AJBS, AJLX, T2.C_NAME AS AJLXMC, DEPTCODE, AH,
                     AJJZJD, 
                     TO_CHAR(T1.LARQ,''yyyymmdd'') LARQ, 
                     TO_CHAR(T1.JARQ,''yyyymmdd'') JARQ,
                     LAAY, CBSPT, CBSPTBS, CBR, 
                     T3.C_AJLB AJLB,
                     T3.C_NAME AJLBNAME, 
                     T2.C_FB FB,
                     YX, JGJB, FYBZ, DM,
                     ''''JAFS, 
                     ''''JAFSX,
                     TO_CHAR(T1.LASTUPDATE,''yyyymmdd'') LASTUPDATE
                FROM SGY.BUF_EAJ T1, 
                     DCADM.DC_AJLXLIST T2,
                     DCADM.DC_AJLBLIST T3,
                     SGY.SPZT_ZZJG T4
               WHERE T1.AJLX = T2.C_AJLX
                 AND DEPTCODE LIKE ''5126%''
                 AND T2.C_AJLB = T3.C_AJLB
                 AND T1.CBSPTBS = T4.ZZJGBZ
                 AND T1.AJBS = '||adAjbs||'';
          Open arAjxx For vSql;
        END IF;
      ELSE
        IF vAjjzjd >= 7 THEN
          vSql := 'SELECT T1.AJBS, AJLX, T2.C_NAME AS AJLXMC, DEPTCODE, AH,
                     AJJZJD, 
                     TO_CHAR(T1.LARQ,''yyyymmdd'') LARQ, 
                     TO_CHAR(T1.JARQ,''yyyymmdd'') JARQ,
                     LAAY, CBSPT, CBSPTBS, CBR, 
                     T3.C_AJLB AJLB,
                     T3.C_NAME AJLBNAME, 
                     T2.C_FB FB,
                     '''' YX, '''' JGJB, '''' FYBZ, '''' DM,
                     T5.JAFS, T5.JAFSX,
                     TO_CHAR(T1.LASTUPDATE,''yyyymmdd'') LASTUPDATE
                FROM SGY.BUF_EAJ T1, 
                     DCADM.DC_AJLXLIST T2,
                     DCADM.DC_AJLBLIST T3,
                      SGY.'||vAjlxEn||'_JAXX T5
               WHERE T1.AJLX = T2.C_AJLX
                 AND DEPTCODE LIKE ''5126%''
                 AND T2.C_AJLB = T3.C_AJLB
                 AND T1.AJBS = T5.AJBS
                 AND T1.AJBS = '||adAjbs||'';
          Open arAjxx For vSql;
        ELSE
          vSql := 'SELECT T1.AJBS, AJLX, T2.C_NAME AS AJLXMC, DEPTCODE, AH,
                     AJJZJD, 
                     TO_CHAR(T1.LARQ,''yyyymmdd'') LARQ, 
                     TO_CHAR(T1.JARQ,''yyyymmdd'') JARQ,
                     LAAY, CBSPT, CBSPTBS, CBR, 
                     T3.C_AJLB AJLB,
                     T3.C_NAME AJLBNAME, 
                     T2.C_FB FB,
                     '''' YX, '''' JGJB, '''' FYBZ, '''' DM,
                     ''''JAFS, ''''JAFSX,
                     TO_CHAR(T1.LASTUPDATE,''yyyymmdd'') LASTUPDATE
                FROM SGY.BUF_EAJ T1, 
                     DCADM.DC_AJLXLIST T2,
                     DCADM.DC_AJLBLIST T3
               WHERE T1.AJLX = T2.C_AJLX
                 AND DEPTCODE LIKE ''5126%''
                 AND T2.C_AJLB = T3.C_AJLB
                 AND T1.AJBS = '||adAjbs||'';
          Open arAjxx For vSql;
        END IF;
      END IF;
    END IF;
    
    /*IF vSpt IS NOT NULL AND vAjjzjd >= 7 THEN
        vSql := 'SELECT T1.AJBS, AJLX, T2.C_NAME AS AJLXMC, DEPTCODE, AH,
                     AJJZJD, 
                     TO_CHAR(T1.LARQ,''yyyymmdd'') LARQ, 
                     TO_CHAR(T1.JARQ,''yyyymmdd'') JARQ,
                     LAAY, CBSPT, CBSPTBS, CBR, 
                     T3.C_AJLB AJLB,
                     T3.C_NAME AJLBNAME, 
                     T2.C_FB FB,
                     YX, JGJB, FYBZ, DM,
                     T5.JAFS, T5.JAFSX,
                     TO_CHAR(T1.LASTUPDATE,''yyyymmdd'') LASTUPDATE
                FROM SGY.BUF_EAJ T1, 
                     DCADM.DC_AJLXLIST T2,
                     DCADM.DC_AJLBLIST T3,
                     SGY.SPZT_ZZJG T4,
                     SGY.'||vAjlxEn||'_JAQK T5
               WHERE T1.AJLX = T2.C_AJLX
                 AND DEPTCODE LIKE ''5126%''
                 AND T2.C_AJLB = T3.C_AJLB
                 AND T1.CBSPTBS = T4.ZZJGBZ
                 AND T1.AJBS = T5.AJBS
                 AND T1.AJBS = '||adAjbs||'';
        Open arAjxx For vSql;
    ELSE
        vSql := 'SELECT T1.AJBS, AJLX, T2.C_NAME AS AJLXMC, DEPTCODE, AH,
                     AJJZJD, 
                     TO_CHAR(T1.LARQ,''yyyymmdd'') LARQ, 
                     TO_CHAR(T1.JARQ,''yyyymmdd'') JARQ,
                     LAAY, CBSPT, CBSPTBS, CBR, 
                     T3.C_AJLB AJLB,
                     T3.C_NAME AJLBNAME, 
                     T2.C_FB FB,
                     '''' YX, '''' JGJB, '''' FYBZ, '''' DM,
                     T5.JAFS, T5.JAFSX,
                     TO_CHAR(T1.LASTUPDATE,''yyyymmdd'') LASTUPDATE
                FROM SGY.BUF_EAJ T1, 
                     DCADM.DC_AJLXLIST T2,
                     DCADM.DC_AJLBLIST T3,
                      SGY.'||vAjlxEn||'_JAQK T5
               WHERE T1.AJLX = T2.C_AJLX
                 AND DEPTCODE LIKE ''5126%''
                 AND T2.C_AJLB = T3.C_AJLB
                 AND T1.AJBS = T5.AJBS
                 AND T1.AJBS = '||adAjbs||'';
        Open arAjxx For vSql;
    END IF;*/
    
    --上抗诉信息  如果为法标外案件c_fb=1，为空
    
    --Open arSksfw For Select null from dual where rownum <1;
    IF vFb='1'OR UPPER(vAjlxEn)='ZXAJ' THEN
      Open arSksfw For Select null from dual where rownum <1;
    ELSE
    vSql := 'SELECT *
               FROM SGY.'||vAjlxEn||'_SKSXX  WHERE AJBS = '''||adAjbs||''' AND DEPT_CODE LIKE ''5126%''';
    Open arSksfw For vsql;
    END IF;
    --案件参与人信息    
    Open arCyrxx For
         SELECT AJBS, ID, REG_TIME, UPDATE_TIME, DEPT_CODE, APP_CODE, 
                XH, LY, BS, JS, LX, MC, RYBM, XB, CSRQ, NL, 
                GJ, MZ, SF, SFZHM, XYJRSF, JGZWXZ, JGGBJB, QTZJZL, 
                QTZJHM, ZY, WHCD, HYZK, ZZMM, XZJB, SZDW, ZW, TSSF, 
                TSSLHBL, HJSZD, JL, LDRY, WCNRJTBJ, JTJJZK, BM, CSD, 
                YJ, SSSQ, GJDQ, ZZJGDM, DWXZ, TSHY, FDDBR, DBRZJZL, 
                DBRZJHM, JGBM, DZ, YZBM, LXDH, DZYX, QTLXFF, LSXM, 
                LSMC, LSZCD, SFFLYZ 
       FROM SGY.YASTML_YASTML
      WHERE DEPT_CODE LIKE '5126%'
        AND AJBS = ''||adAjbs||'';
  --执行措施信息
    Open arZxcsxx For
         SELECT AJBS, ID, REG_TIME, UPDATE_TIME, DEPT_CODE, APP_CODE, 
                JSRQ, CBSPTBS, CBSPT, CBR, GXYJ, TQZJJH, 
                FYSJDQZJ, DYSJ, AJSJ, AJDXYX, WTSX, STFY, 
                SZDHYSFY, KTSL, ZXBZCS, SBZQRQ, SBZQJE, YWRLXCN, 
                HJRQ, SQRFQZQRQ, SQRFQZQJE, ZXFS, FQZX, ZDLXJE, 
                FHJE, BXZXJE, WZXJE, SFPSYCJ, SFWS, DSRKJZX, 
                ZXNR, SFTZCL, TZSPSJ, TZCLR, TZCLYJ, SFYZCL, 
                YZSPYJ, YZCLR, YZCLYJ, JLRS, FKRS, ZDJGFS, FARQ 
       FROM SGY.ZXAJ_AJZXQK
      WHERE DEPT_CODE LIKE '5126%'
        AND AJBS = ''||adAjbs||'';
  --执行案款信息
    Open arZxak For
         SELECT AJBS, ID, REG_TIME, UPDATE_TIME, DEPT_CODE, APP_CODE, 
                KWLX, KWSYRHJSR, JNHFFJE, JFZHJZH, JNHFFWP, JNHFFRQ 
       FROM SGY.ZXAJ_ZXKW
      WHERE DEPT_CODE LIKE '5126%'
        AND AJBS = ''||adAjbs||'';
    Open arZxqk For
         select 1 anFlag from DUAL;
    Open arJafs For
         select 1 anFlag from DUAL;
    Open arJafs1 For
         select 1 anFlag from DUAL;
    

    anFlag := 1;
    avErrmsg := 'OK';
    Return;

  Exception
    When Others Then
        vSqlerrm  :=  Sqlerrm;
        Rollback;
        RAISE_APPLICATION_ERROR(-20001,'断点:'|| avErrmsg ||' 错误信息:'||vSqlerrm);
        Return;
  End;
