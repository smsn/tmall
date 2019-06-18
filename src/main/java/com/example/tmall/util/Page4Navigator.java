package com.example.tmall.util;

import java.util.List;

import org.springframework.data.domain.Page;

/**
 * 显示页面分页
 */
public class Page4Navigator<T> {

    private Page<T> pageFromJPA;
    // 显示页数
    private int navigatePages;
    // 总页数
    private int totalPages;
    // 当前页
    private int number;
    private long totalElements;
    private int size;
    private int numberOfElements;
    private List<T> content;
    private boolean isHasContent;
    private boolean first;
    private boolean last;
    private boolean isHasNext;
    private boolean isHasPrevious;
    private int[] navigatepageNums;

    public Page4Navigator() {

    }

    public Page4Navigator(Page<T> pageFromJPA, int navigatePages) {
        this.pageFromJPA = pageFromJPA;
        this.navigatePages = navigatePages;
        totalPages = pageFromJPA.getTotalPages();
        number = pageFromJPA.getNumber();
        totalElements = pageFromJPA.getTotalElements();
        size = pageFromJPA.getSize();
        numberOfElements = pageFromJPA.getNumberOfElements();
        content = pageFromJPA.getContent();
        isHasContent = pageFromJPA.hasContent();
        first = pageFromJPA.isFirst();
        last = pageFromJPA.isLast();
        isHasNext = pageFromJPA.hasNext();
        isHasPrevious = pageFromJPA.hasPrevious();
        calc();
    }

    public void calc() {
        int showPageNums[];
        int totalPages = getTotalPages();
        int number = getNumber();

        if (totalPages <= navigatePages) {
            showPageNums = new int[totalPages];
            for (int i = 0; i < totalPages; i++) {
                showPageNums[i] = i+1;
            }
        } else {
            showPageNums = new int[navigatePages];
            int startNum = number - navigatePages/2;
            int endNum = number + navigatePages/2;
            if (startNum < 1) {
                // endNum = endNum - startNum + 1;
                startNum = 1;
            } else if (endNum > totalPages) {
                startNum = startNum - (endNum - totalPages);
                // endNum = totalPages;
            }
            for (int i = 0; i < navigatePages; i++) {
                showPageNums[i] = startNum++;
            }
        }
        this.navigatepageNums = showPageNums;
    }

    public int getNavigatePages() {
        return navigatePages;
    }

    public void setNavigatePages(int navigatePages) {
        this.navigatePages = navigatePages;
    }

    public int[] getNavigatepageNums() {
        return navigatepageNums;
    }

    public void setNavigatepageNums(int[] navigatepageNums) {
        this.navigatepageNums = navigatepageNums;
    }

    public int getTotalPages() {
        return totalPages;
    }

    public void setTotalPages(int totalPages) {
        this.totalPages = totalPages;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public long getTotalElements() {
        return totalElements;
    }

    public void setTotalElements(long totalElements) {
        this.totalElements = totalElements;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public int getNumberOfElements() {
        return numberOfElements;
    }

    public void setNumberOfElements(int numberOfElements) {
        this.numberOfElements = numberOfElements;
    }

    public List<T> getContent() {
        return content;
    }

    public void setContent(List<T> content) {
        this.content = content;
    }

    public boolean isHasContent() {
        return isHasContent;
    }

    public void setHasContent(boolean isHasContent) {
        this.isHasContent = isHasContent;
    }

    public boolean isFirst() {
        return first;
    }

    public void setFirst(boolean first) {
        this.first = first;
    }

    public boolean isLast() {
        return last;
    }

    public void setLast(boolean last) {
        this.last = last;
    }

    public boolean isHasNext() {
        return isHasNext;
    }

    public void setHasNext(boolean isHasNext) {
        this.isHasNext = isHasNext;
    }

    public boolean isHasPrevious() {
        return isHasPrevious;
    }

    public void setHasPrevious(boolean isHasPrevious) {
        this.isHasPrevious = isHasPrevious;
    }
}