import { render, screen } from "@testing-library/react";
import "@testing-library/jest-dom";
import Sidebar from "../components/Sidebar";

test("should render landing component", () => {
    render(<Sidebar />);
  const homeElement = screen.getByTestId("side-bar");
  expect(homeElement).toBeInTheDocument();
});