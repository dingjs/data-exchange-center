create global temporary table MONITOR_TEMP_XSJCYJWJ
(
  lastupdate DATE,
  jbfy       VARCHAR2(6),
  fymc       VARCHAR2(50),
  xs         NUMBER,
  jc         NUMBER,
  yj         NUMBER,
  wj         NUMBER
)
on commit delete rows;

create  table MONITOR_XSJCYJWJ
(
  lastupdate DATE,
  jbfy       VARCHAR2(6),
  fymc       VARCHAR2(50),
  xs         NUMBER,
  jc         NUMBER,
  yj         NUMBER,
  wj         NUMBER
);
