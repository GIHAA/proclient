import React, { useState, useEffect } from "react"
import { toast } from "react-toastify"
import Sidebar from "./Sidebar"
import Placeholder from "./PlaceHolder"
import { PdfGenerator } from "../utils/pdfGenerator"
import CreateCustomerForm from "../components/CreateCustomerForm"
import customerService from "../api/customerService"
import EditCustomerForm from "../components/EditCustomerForm"

const Home = () => {
  const [displayCreateFrom, setDisplayCreateFrom] = useState(false)
  const [displayUpdateFrom, setDisplayUpdateForm] = useState(false)
  const [data, setData] = useState([])
  const [showDropdown, setShowDropdown] = useState(false)
  const [selectedItemId, setSelectedItemId] = useState(null)
  const [updateFormData, setUpdateFormdata] = useState({})
  const [target, setTarget] = useState({})
  const [searchText, setSearchText] = useState("")
  const [searchTimeout, setSearchTimeout] = useState(null)
  const [searchedResults, setSearchedResults] = useState([])
  const [loading, setLoading] = useState(true)


  const toggleDropdown = (itemId) => {
    setShowDropdown(!showDropdown)
    setSelectedItemId(itemId)
  }


  const onUpdateChange = (e) =>
    setUpdateFormdata({ ...updateFormData, [e.target.name]: e.target.value })


  const fetchData = async () => {
    setLoading(true)
    try {
      const response = await customerService.getAllCustomers()

      if (response.status === 200) {
        console.log(response.data.results)
        setData(response.data.results)
        setSearchedResults(response.data.results)
        setLoading(false)
      } else {
        console.error("Error fetching data:", response.statusText)
        setLoading(false)
      }
    } catch (error) {
      console.error("Error fetching data:", error)
      setLoading(false)
    }
  }

  useEffect(() => {
    fetchData()
    setLoading(false)
  }, [])

  const handleShow = (itemId) => {
    console.log(`Show item with ID: ${itemId}`)
  }


  const handleDelete = async (cus) => {
    try {
      toast.warn(
        <div>
          <p
            className="text-red-700 ml-8"
            style={{ fontSize: "1.2rem", padding: "1rem" }}
          >
            Are sure you want to remove {cus.firstName}
          </p>
          <div style={{ display: "flex", justifyContent: "center" }}>
            <button
              style={{
                marginRight: "1rem",
                fontSize: "1.2rem",
                padding: "0.5rem 1rem",
              }}
              onClick={async () => {
                const response = await customerService.deleteCustomer(cus.id)
    
                if (response.status === 204) {
                  toast.success(`${cus.firstName} removed successfully`)
                  setData((prevItems) =>
                    prevItems.filter((data) => data.id !== cus.id),
                  )
                  setSearchedResults((prevItems) =>
                    prevItems.filter((data) => data.id !== cus.id),
                  )
                } else {
                  toast.error("Removing failed")
                }
              }}
            >
              Yes
            </button>
            <button
              style={{ fontSize: "1.2rem", padding: "0.5rem 1rem" }}
              onClick={() => toast.dismiss()}
            >
              No
            </button>
          </div>
        </div>,
        { autoClose: false },
      )
    } catch (error) {
      toast.error(error)
    }
  }


  const filterPrompts = (searchtext : string) => {
    const regex = new RegExp(searchtext, "i")
    return data.filter(
      (one) => regex.test(one.firstName) || regex.test(one.lastName),
    )
  }

  const handleSearchChange = (e) => {
    clearTimeout(searchTimeout)
    setSearchText(e.target.value)
    setSearchTimeout(
      setTimeout(() => {
        const searchResult = filterPrompts(e.target.value)
        setSearchedResults(searchResult)
      }, 500),
    )
  }



  return (
    <>
      <Sidebar />

      <div className=" md:ml-64 h-auto ">
        <div className=" rounded-lg  dark:border-gray-600 h-screen ">
          <section className="bg-gray-50 dark:bg-gray-900 h-screen p-3 sm:p-5">
            <div className=" mx-auto max-w-screen-xl px-4 lg:px-2  pt-[50px]">
              <div className="bg-white mt-[10px] dark:bg-gray-800 relative shadow-md sm:rounded-lg overflow-hidden">
                <div className="flex flex-col md:flex-row items-center justify-between space-y-3 md:space-y-0 md:space-x-4 p-4">
                  <div className="w-full md:w-1/2 ">
                    <form className="flex items-center">
                      <label for="simple-search" className="sr-only">
                        Search
                      </label>
                      <div className="relative w-full">
                        <div className="absolute inset-y-0 left-0 flex items-center pl-3 pointer-events-none">
                          <svg
                            aria-hidden="true"
                            className="w-5 h-5 text-gray-500 dark:text-gray-400"
                            fill="currentColor"
                            viewBox="0 0 20 20"
                            xmlns="http://www.w3.org/2000/svg"
                          >
                            <path
                              fillRule="evenodd"
                              d="M8 4a4 4 0 100 8 4 4 0 000-8zM2 8a6 6 0 1110.89 3.476l4.817 4.817a1 1 0 01-1.414 1.414l-4.816-4.816A6 6 0 012 8z"
                              clipRule="evenodd"
                            />
                          </svg>
                        </div>
                        <input
                          type="text"
                          id="simple-search"
                          className="bg-gray-50 border border-gray-300 text-gray-900 text-sm rounded-lg focus:ring-primary-500 focus:border-primary-500 block w-full pl-10 p-2 dark:bg-gray-700 dark:border-gray-600 dark:placeholder-gray-400 dark:text-white dark:focus:ring-primary-500 dark:focus:border-primary-500"
                          placeholder="Search"
                          onChange={handleSearchChange}
                        />
                      </div>
                    </form>
                  </div>
                  <div className="w-full md:w-auto flex flex-col md:flex-row space-y-2 md:space-y-0 items-stretch md:items-center justify-end md:space-x-3 flex-shrink-0">
                    <button
                      type="button"
                      className="inline-flex items-center px-5 py-2.5  text-sm font-medium text-center text-white bg-blue-700 rounded-lg focus:ring-4 focus:ring-blue-200 dark:focus:ring-primary-900 hover:bg-blue-800"
                      onClick={() => {
                        setDisplayCreateFrom(true)
                      }}
                    >
                      <svg
                        className="h-3.5 w-3.5 mr-2"
                        fill="currentColor"
                        viewBox="0 0 20 20"
                        xmlns="http://www.w3.org/2000/svg"
                        aria-hidden="true"
                      >
                        <path
                          clipRule="evenodd"
                          fillRule="evenodd"
                          d="M10 3a1 1 0 011 1v5h5a1 1 0 110 2h-5v5a1 1 0 11-2 0v-5H4a1 1 0 110-2h5V4a1 1 0 011-1z"
                        />
                      </svg>
                      Add Customer
                    </button>

                    <button
                      type="button"
                      className="inline-flex items-center px-5 py-2.5  text-sm font-medium text-center text-white bg-blue-700 rounded-lg focus:ring-4 focus:ring-blue-200 dark:focus:ring-primary-900 hover:bg-blue-800"
                      onClick={() => {
                        //PdfGenerator()
                      }}
                    >
                      Export as PDF
                    </button>
                  </div>
                </div>
                {loading ? (
                  <>
                    <div className="flex justify-center items-center h-[420px] ">
                      <Placeholder />
                    </div>
                  </>
                ) : (
                  <>
                    <div className="max-h-[520px] h-screen overflow-y-auto">
                      <table className="w-full text-sm text-left text-gray-500 dark:text-gray-400">
                        <thead className="text-xs text-gray-700 uppercase bg-gray-50 dark:bg-gray-700 dark:text-gray-400">
                          <tr>
                            <th scope="col" className="px-4 py-3">
                              Id
                            </th>
                            <th scope="col" className="px-4 py-3">
                              Full Name
                            </th>
                            <th scope="col" className="px-4 py-3">
                              Email
                            </th>
                            <th scope="col" className="px-4 py-3">
                              Gender
                            </th>
                            <th scope="col" className="px-4 py-3">
                              Dob
                            </th>
                            <th scope="col" className="px-4 py-3">
                              Contact
                            </th>
                            <th scope="col" className="px-4 py-3">
                              <span className="sr-only">Actions</span>
                            </th>
                          </tr>
                        </thead>
                        <tbody>
                          {searchedResults.map((item) => {
                             const dob = new Date(item.dob);
                             const currentDate = new Date();
                             const age = currentDate.getFullYear() - dob.getFullYear();

                             
                            return (
                              <>
                                <tr
                                  key={item.id}
                                  className="border-b dark:border-gray-700"
                                  onClick={() => {
                            
                                  }}
                                >
                                  <th
                                    scope="row"
                                    className="px-4 py-3 font-medium text-gray-900 whitespace-nowrap dark:text-white"
                                  >
                                    {item.id}7&#34;
                                  </th>
                                  <td className="px-4 py-3">{item.firstName}  {item.lastName}</td>
                                  <td className="px-4 py-3">
                                    {item.email}
                                  </td>
                                  <td className="px-4 py-3">{item.gender}</td>
                                  <td className="px-4 py-3">{age}</td>
                                  <td className="px-4 py-3">+{item.phoneNumber}</td>

                                  <td className="px-4 py-3 flex items-center justify-end relative">
                                    <button
                                      id={`dropdown-button-${item.id}`}
                                      onClick={() => toggleDropdown(item.id)}
                                      className="inline-flex items-center p-0.5 text-sm font-medium text-center text-gray-500 hover:text-gray-800 rounded-lg focus:outline-none dark:text-gray-400 dark:hover:text-gray-100"
                                      type="button"
                                    >
                                      <svg
                                        className="w-5 h-5 rotate-90"
                                        aria-hidden="true"
                                        fill="currentColor"
                                        viewBox="0 0 20 20"
                                        xmlns="http://www.w3.org/2000/svg"
                                      >
                                        <path d="M6 10a2 2 0 11-4 0 2 2 0 014 0zM12 10a2 2 0 11-4 0 2 2 0 014 0zM16 12a2 2 0 100-4 2 2 0 000 4z" />
                                      </svg>
                                    </button>
                                    {showDropdown &&
                                      selectedItemId === item.id && (
                                        <div
                                          className="absolute z-10 w-40 bg-white rounded-lg shadow-lg divide-y divide-gray-200 dark:bg-gray-700 dark:divide-gray-600"
                                          style={{ top: 10, right: 40 }}
                                        >
                                          <ul
                                            className="py-1 text-sm text-gray-700 dark:text-gray-200"
                                            aria-labelledby={`dropdown-button-${item.id}`}
                                          >
                                            <li>
                                    <a
                                      href="#"
                                      onClick={() => handleShow(item.id)}
                                      className="block py-2 px-4 hover:bg-gray-100 dark:hover:bg-gray-600 dark:hover:text-white"
                                    >
                                      Show
                                    </a>
                                  </li>
                                            <li>
                                              <a
                                                href="#"
                                                onClick={() => {
                                                  setDisplayUpdateForm(true)
                                                  setTarget(item)
                                                  toggleDropdown(item.id)
                                                  setUpdateFormdata(item)
                                                }}
                                                className="block py-2 px-4 hover:bg-gray-100 dark:hover:bg-gray-600 dark:hover:text-white"
                                              >
                                                Edit
                                              </a>
                                            </li>

                                            <li>
                                              <a
                                                href="#"
                                                onClick={() =>
                                                  handleDelete(item)
                                                }
                                                className="block py-2 px-4 text-sm text-gray-700 hover:bg-gray-100 dark:hover:bg-gray-600 dark:text-gray-200 dark:hover:text-white"
                                              >
                                                Delete
                                              </a>
                                            </li>
                                          </ul>
                                        </div>
                                      )}
                                  </td>
                                </tr>
                              </>
                            )
                          })}
                        </tbody>
                      </table>
                    </div>
                  </>
                )}
              </div>
            </div>
          </section>
        </div>
      </div>

      {displayUpdateFrom && (
        <EditCustomerForm setDisplayUpdateForm={setDisplayUpdateForm}  fetchData={fetchData} target={target} />
      )}

      {displayCreateFrom && (
        <CreateCustomerForm  setDisplayCreateFrom={setDisplayCreateFrom}  fetchData={fetchData} />
      )}

      
    </>
  )
}

export default Home
