import { toast } from 'react-toastify'; 
import jsPDF from 'jspdf';
import 'jspdf-autotable';

export const PdfGenerator = (searchedResults : any) => {
  toast.info("Generating Report");
  const name = "Inventory";
  const pdf_title = "Inventory Report";
  const pdf_address = "info@sneakerhub.com";
  const pdf_phone = "+94 11 234 5678";
  const pdf_email = "Address: No 221/B, Peradeniya Road, Kandy";

  const doc = new jsPDF("landscape", "px", "a4", false);
  const today = new Date();
  const date = `${today.getFullYear()}-${today.getMonth() + 1}-${today.getDate()}`;

  const title = `${pdf_title}`;
  doc.setFont("helvetica");
  doc.setTextColor("#000000");

  // Add title and date
  doc.setFontSize(24);
  doc.text(title, 20, 30);
  doc.setFontSize(12);
  doc.setTextColor("#999999");
  doc.text(`Generated on ${date}`, 20, 40);
  // doc.addImage(logo, "JPG", 20, 60, 70, 40);
  doc.setFontSize(16);
  doc.setFont("helvetica", "bold");
  doc.setTextColor("#000000");
  doc.text("Sneaker Hub", 100, 70);
  doc.setFont("helvetica", "normal");
  doc.setFontSize(10);
  doc.setTextColor("#999999");
  doc.text(`Tel: ${pdf_phone}}`, 100, 80);
  doc.text(`Email: ${pdf_email}`, 100, 90);
  doc.text(`Address: ${pdf_address}`, 100, 100);
  doc.line(20, 110, 600, 110);

  // Add table with data
  doc.setTextColor("#999999");
  doc.setFontSize(12);
  doc.setTextColor("#000000");

  doc.autoTable({
    startY: 130,
    head: [["Id", "Name", "Category", "Quantity", "Price"]],
    body: searchedResults.map((request: any) => [
      request.id,
      request.itemName,
      request.category,
      request.quantity,
      request.price,
    ]),
    theme: "grid",
  });

  doc.save(`${name}.pdf`);
};