package data.exchange.center.service.swh.mapper.sgy;

import org.apache.ibatis.annotations.Mapper;

import data.exchange.center.service.swh.domain.EaJDa;
import data.exchange.center.service.swh.domain.EajDaMl;
import data.exchange.center.service.swh.domain.EaJz;
import data.exchange.center.service.swh.domain.EaJJZMl;
import data.exchange.center.service.swh.domain.Eaj;
import data.exchange.center.service.swh.domain.EajFtsy;
import data.exchange.center.service.swh.domain.EajGxyy;
import data.exchange.center.service.swh.domain.EajHbqk;
import data.exchange.center.service.swh.domain.EajMtbq;
import data.exchange.center.service.swh.domain.EajQxbg;
import data.exchange.center.service.swh.domain.EajSala;
import data.exchange.center.service.swh.domain.EajSjqk;
import data.exchange.center.service.swh.domain.EajSl;
import data.exchange.center.service.swh.domain.EajSpxy;
import data.exchange.center.service.swh.domain.EajXsfzdx;
import data.exchange.center.service.swh.domain.EajYaxx;
import data.exchange.center.service.swh.domain.EajZj;
import data.exchange.center.service.swh.domain.Edsr;
import data.exchange.center.service.swh.domain.EdsrDzlx;
import data.exchange.center.service.swh.domain.EdsrQklj;
import data.exchange.center.service.swh.domain.EdsrQt;
import data.exchange.center.service.swh.domain.EdsrQzcs;
import data.exchange.center.service.swh.domain.EdsrZkzm;
import data.exchange.center.service.swh.domain.Edsrlxqj;
import data.exchange.center.service.swh.domain.EfTzcd;

@Mapper
public interface SwhSgyDataMapper {

    int deleteEaj(String ahdm) throws Exception;

    int deleteEajYaxx(String ahdm) throws Exception;

    int deleteEajSala(String ahdm) throws Exception;

    int deleteEajSl(String ahdm) throws Exception;

    int deleteEajSjqk(String ahdm) throws Exception;

    int deleteEajHbqk(String ahdm) throws Exception;

    int deleteEajQxbg(String ahdm) throws Exception;

    int deleteEajMtbq(String ahdm) throws Exception;

    int deleteEajFtsy(String ahdm) throws Exception;

    int deleteEajGxyy(String ahdm) throws Exception;

    int deleteEajZj(String ahdm) throws Exception;

    int deleteEajSpxy(String ahdm) throws Exception;

    int deleteEdsr(String ahdm) throws Exception;

    int deleteEdsrQzcs(String ahdm) throws Exception;

    int deleteEdsrQklj(String ahdm) throws Exception;

    int deleteEdsrZkzm(String ahdm) throws Exception;

    int deleteEdsrDzlx(String ahdm) throws Exception;

    int deleteEdsrlxqj(String ahdm) throws Exception;

    int deleteEfTzcd(String ahdm) throws Exception;

    int deleteEajXsfzdx(String ahdm) throws Exception;

    int deleteEdsrQt(String ahdm) throws Exception;

    int deleteEaJz(String ahdm) throws Exception;

    int deleteEaJzMl(String ahdm) throws Exception;

    int deleteEaJDa(String ahdm) throws Exception;

    int deleteEaJDaMl(String ahdm) throws Exception;

    int pushEaj(Eaj eaj) throws Exception;

    int pushEajYaxx(EajYaxx eajYaxx) throws Exception;

    int pushEajSala(EajSala eajSala) throws Exception;

    int pushEajSl(EajSl eajSl) throws Exception;

    int pushEajSjqk(EajSjqk eajSjqk) throws Exception;

    int pushEajHbqk(EajHbqk eajHbqk) throws Exception;

    int pushEajQxbg(EajQxbg eajQxbg) throws Exception;

    int pushEajMtqb(EajMtbq eajMtbq) throws Exception;

    int pushEajFtsy(EajFtsy eajFtsy) throws Exception;

    int pushEajGxyy(EajGxyy eajGxyy) throws Exception;

    int pushEajZj(EajZj eajZj) throws Exception;

    int pushEajSpxy(EajSpxy eajSpxy) throws Exception;

    int pushEdsr(Edsr edsr) throws Exception;

    int pushEdsrQzcs(EdsrQzcs edsrQzcs) throws Exception;

    int pushEdsrQklj(EdsrQklj edsrQklj) throws Exception;

    int pushEdsrZkzm(EdsrZkzm edsrZkzm) throws Exception;

    int pushEdsrDzlx(EdsrDzlx edsrDzlx) throws Exception;

    int pushEdsrlxqj(Edsrlxqj edsrlxqj) throws Exception;

    int pushEfTzcd(EfTzcd efTzcd) throws Exception;

    int pushEajXsfzdx(EajXsfzdx eajXsfzdx) throws Exception;

    int pushEdsrQt(EdsrQt edsrQt) throws Exception;

    int pushEaJz(EaJz eaJz) throws Exception;

    int pushEaJzMl(EaJJZMl eaJzMl) throws Exception;

    int pushEaJDa(EaJDa eaJDa) throws Exception;

    int pushEaJDaMl(EajDaMl eaJDaMl) throws Exception;
}
