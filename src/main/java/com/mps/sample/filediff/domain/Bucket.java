package com.mps.sample.filediff.domain;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "buckets")
public class Bucket {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String name;
    private byte[] rightFile;
    private byte[] leftFile;

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "diff_id")
    private FileDiff diff;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public byte[] getRightFile() {
        return rightFile;
    }

    public void setRightFile(byte[] rightFile) {
        this.rightFile = rightFile;
    }

    public byte[] getLeftFile() {
        return leftFile;
    }

    public void setLeftFile(byte[] leftFile) {
        this.leftFile = leftFile;
    }

    public FileDiff getDiff() {
        return diff;
    }

    public void setDiff(FileDiff diff) {
        this.diff = diff;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Bucket bucket = (Bucket) o;
        return id == bucket.id && name.equals(bucket.name) && Objects.equals(diff, bucket.diff);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, diff);
    }

    @Override
    public String toString() {
        return "Bucket{" +
                "name='" + name + '\'' +
                ", diff=" + diff +
                '}';
    }
}
