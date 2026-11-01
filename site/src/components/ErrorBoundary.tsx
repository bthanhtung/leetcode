import { Component, type ReactNode } from "react";

/** Keeps a failure in one panel from blanking the whole page. */
export class ErrorBoundary extends Component<{ children: ReactNode }, { failed: boolean }> {
  state = { failed: false };

  static getDerivedStateFromError() {
    return { failed: true };
  }

  render() {
    if (!this.state.failed) return this.props.children;
    return <p className="p-6 text-sm text-muted-foreground">Phần này gặp lỗi khi hiển thị. Tải lại trang hoặc thử dữ liệu khác.</p>;
  }
}
