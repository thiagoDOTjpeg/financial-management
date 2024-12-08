package br.com.gritti.app.dto;

import org.springframework.data.domain.Page;

import java.util.List;

public class PaginationDTO<T> {

  private List<T> content;
  private int currentPage;
  private int totalPages;
  private long totalElements;

  public PaginationDTO(Page<T> page) {
    this.content = page.getContent();
    this.currentPage = page.getNumber();
    this.totalPages = page.getTotalPages();
    this.totalElements = page.getTotalElements();
  }

  public List<T> getContent() {
    return content;
  }

  public void setContent(List<T> content) {
    this.content = content;
  }

  public int getCurrentPage() {
    return currentPage;
  }

  public void setCurrentPage(int currentPage) {
    this.currentPage = currentPage;
  }

  public int getTotalPages() {
    return totalPages;
  }

  public void setTotalPages(int totalPages) {
    this.totalPages = totalPages;
  }

  public long getTotalElements() {
    return totalElements;
  }

  public void setTotalElements(long totalElements) {
    this.totalElements = totalElements;
  }
}
