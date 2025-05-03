package com.erp.erp.entity;
import com.erp.erp.enums.POSITION;
import com.erp.erp.enums.ParamTyPE;
import com.erp.erp.enums.Unity;
import jakarta.persistence.*;
import lombok.*;
import java.util.List;
import jakarta.persistence.*;

import javax.swing.text.Position;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Data
public class ParameterType {
        @Id
        private Long id;
        private String name;
        @Enumerated(EnumType.STRING)
        private Unity unit;
        @Enumerated(EnumType.STRING)
        private ParamTyPE paramType;
        @Enumerated(EnumType.STRING)
        private POSITION position;

        private Double min;
        private Double max;

        @ManyToOne
        @JoinColumn(name = "equipment_id")
        private Equipment equipment;

        @OneToMany(mappedBy = "parameterType", cascade = CascadeType.ALL, orphanRemoval = true)
        private List<DailyParameter> dailyParameters;


}
