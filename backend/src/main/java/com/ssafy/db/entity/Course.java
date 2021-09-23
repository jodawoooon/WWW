package com.ssafy.db.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicInsert;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "course")
public class Course {

    // 2
    @Id
    @Column(name = "course_id")
    private int courseId;

    // 거북이 마을 솔바람길
    @Column(name = "flag_name")
    private String flagName;

    // 01코스
    @Column
    private String name;

    // 전통체험관~보살바위~말바위~자라바위~전용석고택~호랑이가 잡아준묘~사랑바위~전통체험관(2.1km)
    @Column(length = 4000)
    private String route;

    // 2.1 (KM 단위)
    @Column
    private double distance;

    // 보통 (쉬움, 보통, 어려움)
    @Column
    private String level;

    // 1시간 (프론트엔드 출력용 원본 데이터 string값)
    @Column
    private String time;

    // 60 (정렬 및 필터 검색용 int형 분 단위, 2시간 20분의 경우 140)
    // 승용차 30분, 4박 5일등 분 단위로 변경 불가능한 경우 1440으로 초기화
    @Column(name = "time_int")
    private int timeInt;

    // 마을의 모양이 거북이의 목처럼 생겨 구목(구을목)이라고 하고, 거북모양의 바위가 머리를 안쪽으로 향하고 있어 내현이라고 한다. 아홉가지의 보물을 덮고 있다는 보개산의 아늑함을 배경으로 500년 이상 수령의 느티나무, 철따라 피고지는 야생화, 군데 군데 위용을 드러내는 바위, 고풍스러운 전통가옥이 어우러져 한폭의 동양화 같은 천혜의 자연환경을 갖추고 있다.『동창이 밝았느냐 노고지리 우지진다-권농가』의 저자인 남구만 선생이 사신곳으로 농촌전통테마마을과 국내 최초 농어촌인성학교로 지정 되어 많은 이들의 발길을 옮기고 있다.
    // 산책로에 대한 추가 설명
    @Column(length = 4000)
    private String detail;

    // 식수보급처가 없으니 매점에서 구입하거나 사전준비
    // 주변 관광지 또는 특이사항에 대한 설명
    @Column(length = 500, name="`option`")
    private String option;

    // 전통체험관, 마을화장실
    @Column(length = 500)
    private String toilet;

    // 홍성버스터미널 매점
    // 매점, 편의점에 대한 설명
    @Column(name = "conv_store")
    private String convStore;

    // 36.5714867
    @Column
    private double latitude;

    // 126.6168424
    @Column
    private double longitude;

    // 충남 홍성군 구항면 내현리 353-1
    // (서울, 대전, 광주, 대구 등) 특별시, 광역시를 제외하고 (서울, 대전)처럼 이름만 넣어서 검색 필요
    @Column(length = 200)
    private String address;

}