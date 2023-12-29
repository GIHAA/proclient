import React from 'react';
import { render, screen, fireEvent } from '@testing-library/react';
import CreateCustomerForm from '../components/CreateCustomerForm';
import CustomerService from '../api/cusService';

describe('CreateCustomerForm', () => {
  test('renders CreateCustomerForm component', () => {
    render(<CreateCustomerForm />);
    const addCustomerButton = screen.getByText('Add Customer');
    expect(addCustomerButton).toBeInTheDocument();
  });

});
