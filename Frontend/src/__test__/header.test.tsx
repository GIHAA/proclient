import { render, screen } from "@testing-library/react";
import "@testing-library/jest-dom";
import Header from "../components/Header";

test("should render landing component", () => {
    render(<Header />);
  const homeElement = screen.getByTestId("header");
  expect(homeElement).toBeInTheDocument();
});