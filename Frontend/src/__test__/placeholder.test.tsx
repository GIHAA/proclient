import { render, screen } from "@testing-library/react";
import "@testing-library/jest-dom";
import PlaceHolder from "../components/PlaceHolder";

test("should render landing component", () => {
    render(<PlaceHolder />);
  const homeElement = screen.getByTestId("place-holder");
  expect(homeElement).toBeInTheDocument();
});