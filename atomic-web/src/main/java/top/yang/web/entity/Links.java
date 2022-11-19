package top.yang.web.entity;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;
import top.yang.domain.pojo.BaseEntity;


/**
 * @author Pride_Yang
 */
@EqualsAndHashCode(callSuper = true)
@Table("tb_links")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Links extends BaseEntity {

    /**
     * 友链id
     */
    @Id
    private Long id;

    /**
     * 友链url
     */
    @Column(value = "url")
    private String url;

    /**
     * 友链名称
     */
    @Column(value = "name")
    private String name;

    /**
     * 友链图片
     */
    @Column(value = "image")
    private String image;

    /**
     * 链接跳转形式 _blank	在新窗口中打开被链接文档。 _self	默认。在相同的框架中打开被链接文档。 _parent	在父框架集中打开被链接文档。 _top	在整个窗口中打开被链接文档。 {@link LinkTarget}
     */
    @Column(value = "target")
    private String target;

    /**
     * 友链描述
     */
    @Column(value = "description")
    private String description;

    /**
     * 链接是否可见  0 不可见 1可见
     */
    @Column(value = "visible")
    private String visible;

    /**
     * 友链网站所有者
     */
    @Column(value = "owner")
    private Long owner;

    /**
     * 排序
     */
    @Column(value = "priority")
    private Integer priority;


    @Override
    public boolean isNew() {
        return false;
    }
}

